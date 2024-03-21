package com.buildbuddy.domain.paypal;

import com.buildbuddy.jsonresponse.DataResponse;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class PaypalService {

    @Autowired
    private APIContext apiContext;

    private final String currency = "USD";

    @Value("${spring.paypal.cancel-url}")
    private String cancelUrl;

    @Value("${spring.paypal.success-url}")
    private String successUrl;

    public DataResponse<String> createPayment(Double total) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription("top up build buddy balance");
        transaction.setAmount(amount);

        List<Transaction> transactions = List.of(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        String redirectUrl = null;
        if(createdPayment != null){
            List<Links> links = createdPayment.getLinks();
            redirectUrl = links.stream()
                    .filter(l -> l.getRel().equals("approval_url"))
                    .map(Links::getHref)
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("cant find approval url"));
        }

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .message("Success getting redirect url")
                .httpStatus(HttpStatus.OK)
                .data(redirectUrl)
                .build();
    }

    public DataResponse<String> executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

       payment.execute(apiContext, paymentExecution);

       return DataResponse.<String>builder()
               .timestamp(LocalDateTime.now())
               .message("Success executing payment")
               .httpStatus(HttpStatus.OK)
               .data(payment.getId())
               .build();
    }

    public DataResponse<Object> payout(double amount, String receiverEmail) throws PayPalRESTException {
        PayoutSenderBatchHeader payoutSenderBatchHeader = new PayoutSenderBatchHeader();
        payoutSenderBatchHeader.setSenderBatchId("Payouts_" + LocalDateTime.now().toString());
        payoutSenderBatchHeader.setEmailSubject("Consultant Payment");
        payoutSenderBatchHeader.setRecipientType("EMAIL");
        List<PayoutItem> payoutItems = new ArrayList<>();

        payoutItems.add(new PayoutItem(new Currency(currency, String.format("%.2f", amount)), receiverEmail));
        Payout payout = new Payout();

        payout.setSenderBatchHeader(payoutSenderBatchHeader);
        payout.setItems(payoutItems);

        payout.create(apiContext, null);
        return DataResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Success executing payout")
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
