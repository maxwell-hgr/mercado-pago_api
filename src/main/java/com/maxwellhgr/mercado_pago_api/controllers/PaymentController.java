package com.maxwellhgr.mercado_pago_api.controllers;

import com.maxwellhgr.mercado_pago_api.DTO.PaymentResponseDTO;
import com.maxwellhgr.mercado_pago_api.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{amount}")
    public ResponseEntity<PaymentResponseDTO> payment(@PathVariable double amount) {

        // generate random id -> without collision
        Long id = 123812371283612L;

        PaymentResponseDTO payment = paymentService.createOrder(amount, id).getBody();
        System.out.println("payment response dto: " + payment);
        return ResponseEntity.ok(payment);
    }
}
