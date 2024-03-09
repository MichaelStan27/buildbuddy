package com.buildbuddy.domain.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/paypal")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;

    @PostMapping("/payment/create")
    public ResponseEntity<Object> createPayment(@RequestBody PaypalReqDto body, HttpServletRequest request) throws PayPalRESTException {
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        String redirectUrl = paypalService.createPayment(body.getAmount());

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>(redirectUrl, HttpStatus.OK);
    }

    @GetMapping("/payment/execute")
    public ResponseEntity<Object> executePayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                                                 HttpServletRequest request) throws PayPalRESTException {
        log.info("Received Request on {} - {}", request.getServletPath(), request.getMethod());

        Payment payment = paypalService.executePayment(paymentId, payerId);

        log.info("Success Executing Request on {}", request.getServletPath());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }



}
