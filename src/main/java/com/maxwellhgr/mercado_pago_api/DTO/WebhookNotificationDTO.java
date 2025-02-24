package com.maxwellhgr.mercado_pago_api.DTO;

import java.util.Map;

public record WebhookNotificationDTO(
        String action,
        String application_id,
        Map<String, Object> data,
        String date_created,
        String id,
        boolean live_mode,
        String status,
        String type,
        long user_id,
        int version
) {}
