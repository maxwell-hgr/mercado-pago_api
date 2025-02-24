package com.maxwellhgr.mercado_pago_api.services;

import com.maxwellhgr.mercado_pago_api.DTO.PaymentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${mercado.pago.user.id}")
    private String userId;

    @Value("${mercado.pago.pos}")
    private String pos;

    @Value("${mercado.pago.token}")
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<PaymentResponseDTO> createOrder(double amount, Long id) {
        Map<String, Object> orderRequest = createOrderRequest(amount, id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(orderRequest, headers);

        System.out.println("Order request entity: " + requestEntity);

        return restTemplate.exchange(getOrderUrl(), HttpMethod.POST, requestEntity, PaymentResponseDTO.class);
    }

    private Map<String, Object> createOrderRequest(double amount, Long id) {
        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("external_reference", id);
        orderRequest.put("title", "Investment Order");
        orderRequest.put("description", "Order Description");
        orderRequest.put("total_amount", amount);
        orderRequest.put("expiration_date", "2025-12-31T23:59:59.000-04:00");

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("sku_number", "KS955RUR");
        item.put("category", "marketplace");
        item.put("title", "Marketplace Order");
        item.put("description", "Description");
        item.put("unit_price", amount);
        item.put("quantity", 1);
        item.put("unit_measure", "unit");
        item.put("total_amount", amount);
        items.add(item);

        orderRequest.put("items", items);

        return orderRequest;
    }

    private String getOrderUrl() {
        return "https://api.mercadopago.com/instore/orders/qr/seller/collectors/" + userId + "/pos/" + pos + "/qrs";
    }
}