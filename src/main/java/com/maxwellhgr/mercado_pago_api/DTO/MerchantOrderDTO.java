package com.maxwellhgr.mercado_pago_api.DTO;

import java.util.List;

public record MerchantOrderDTO(
        long id,
        String status,
        String external_reference,
        String preference_id,
        List<Object> shipments,
        List<Object> payouts,
        String marketplace,
        String notification_url,
        String date_created,
        String last_updated,
        String sponsor_id,
        double shipping_cost,
        double total_amount,
        double paid_amount,
        double refunded_amount,
        boolean cancelled,
        String additional_info,
        String application_id,
        boolean is_test,
        String order_status,
        String client_id
) {}

