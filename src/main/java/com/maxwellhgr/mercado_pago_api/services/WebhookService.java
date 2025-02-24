package com.maxwellhgr.mercado_pago_api.services;

import com.maxwellhgr.mercado_pago_api.DTO.MerchantOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    @Value("${mercado.pago.token}")
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public WebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<MerchantOrderDTO> getPaymentDetails(String id) {
        String url = getNotificationUrl(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new org.springframework.http.HttpEntity<>(headers),
                MerchantOrderDTO.class);
    }

    public String getNotificationUrl(String id) {
        return "https://api.mercadolibre.com/merchant_orders/" + id;
    }
}
