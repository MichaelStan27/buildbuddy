package com.buildbuddy.domain.paypal;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.user.entity.BalanceTransaction;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.BalanceTransactionRepository;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.enums.balance.BalanceTransactionStatus;
import com.buildbuddy.enums.balance.BalanceTransactionType;
import com.buildbuddy.jsonresponse.DataResponse;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private BalanceTransactionRepository balanceTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditorAwareImpl audit;

    private final String currency = "USD";

    @Value("${spring.paypal.cancel-url}")
    private String cancelUrl;

    @Value("${spring.paypal.success-url}")
    private String successUrl;

    public DataResponse<String> createPayment(Double total) throws PayPalRESTException {
        log.info("Creating Payment");
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

        log.info("Payment Created");
        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .message("Success getting redirect url")
                .httpStatus(HttpStatus.OK)
                .data(redirectUrl)
                .build();
    }

    @Transactional
    public DataResponse<String> executePayment(String paymentId, String payerId) throws PayPalRESTException {
        log.info("Executing payment");
        UserEntity user = audit.getCurrentAuditor().orElseThrow(() -> new RuntimeException("Not Authenticated"));

        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

       Payment executedPayment = payment.execute(apiContext, paymentExecution);

       Transaction transaction = executedPayment.getTransactions().get(0);

       BigDecimal amount = new BigDecimal(transaction.getAmount().getTotal());
       BalanceTransaction balanceTransaction = BalanceTransaction.builder()
               .nominal(amount)
               .status(BalanceTransactionStatus.ADDED.getValue())
               .transactionType(BalanceTransactionType.PAYPAL.getValue())
               .user(user)
               .build();

       UserEntity tobeUpdateUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User Not Found"));

       tobeUpdateUser.setBalance(tobeUpdateUser.getBalance().add(amount));

       balanceTransactionRepository.saveAndFlush(balanceTransaction);
       userRepository.saveAndFlush(tobeUpdateUser);

       log.info("Payment Executed.");
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
