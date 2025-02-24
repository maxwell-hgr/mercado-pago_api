package com.maxwellhgr.mercado_pago_api.controllers;

import com.maxwellhgr.mercado_pago_api.DTO.MerchantOrderDTO;
import com.maxwellhgr.mercado_pago_api.DTO.WebhookNotificationDTO;
import com.maxwellhgr.mercado_pago_api.security.SignatureValidator;
import com.maxwellhgr.mercado_pago_api.services.WebhookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/api/webhook")
public class WebhookController {

    private final SignatureValidator signatureValidator;
    private final WebhookService webhookService;

    @Autowired
    public WebhookController(SignatureValidator signatureValidator, WebhookService webhookService) {
        this.signatureValidator = signatureValidator;
        this.webhookService = webhookService;
    }

    @PostMapping
    public ResponseEntity<String> handleNotification(
            HttpServletRequest request,
            @RequestParam(name = "data.id", required = false, defaultValue = "") String dataId,
            @RequestBody WebhookNotificationDTO body
    ){
        String xSignature = request.getHeader("x-signature");
        String xRequestId = request.getHeader("x-request-id");

        if (signatureValidator.verifySignature(xSignature, xRequestId, dataId)) {
            System.out.println("Validated signature");

            System.out.println(body.toString());

            if(Objects.equals(body.type(), "topic_merchant_order_wh")){
                if(Objects.equals(body.action(), "update")){

                    // not sure if it's necessary -> make sure webhook notification can confirm the payment
                    MerchantOrderDTO merchantOrderDTO = webhookService.getPaymentDetails(dataId).getBody();
                    assert merchantOrderDTO != null;
                    String status = merchantOrderDTO.order_status();
                    if(status.equals("paid")){
                        // put here your payment confirm logics
                        return ResponseEntity.ok().body("Paid");
                    }
                    return ResponseEntity.ok().body("Updated but not paid");
                }
                return ResponseEntity.ok().body("Merchant order created");
            }
        }
        return ResponseEntity.badRequest().body("Invalid signature");

    };
}
