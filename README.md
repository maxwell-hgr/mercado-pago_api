# API para geração de pagamento QR personalizado + validação - MERCADO PAGO

## Sobre o Projeto
Fiz esse projeto como base para uma aplicação freelance de um cliente que precisava de integração com *API's* de meios de pagamento,
consiste em um **boilerplate** que gera pagamentos personalizados por meio de QR's, bem como realiza a validação desses pagamentos usando criptografia e
*WEBHOOKS*.

## Funcionalidades
- **Gera um pagamento personalizado**
- **Recebe e valida WebHooks de pagamento**
- **Consulta status do pagamento**

## Tecnologias Utilizadas

```
- Java 21
- Spring 3.4.3
- Maven 4.0.0 

Dev:
- IntelliJ
```

## Endpoints

**{*domain*}/api/payment/{*amount*}** -> em amount deve ser setado a quantia em reais (R$) do pagamento gerado

*response*
```json
{
  "in_store_order_id": "123e4567-e89b-12d3-a456-426614174000",
  "qr_data": "00020101021226940014BR.GOV.BCB.PIX2572pix-qr.exemplo.com/instore/o/v2/123e4567-e89b-12d3-a456-4266141740005204000053039865802BR5912LojaExemplo6009RIO CLARO62070503***6304A1B2"
}
```

**{*domain*}/api/webhook** -> esse endpoint recebe a requisição do **MERCADO PAGO** para validar o pagamento, deve ser configurado em: https://www.mercadopago.com.br/developers/panel/app/{seu_appId}/webhooks

## Configurar application.properties (variáveis de ambiente)

mercado.pago.user.id= **User ID** em -> 
https://www.mercadopago.com.br/developers/panel/app/{seu_appId}

mercado.pago.signature= **Assinatura secreta** em -> 
https://www.mercadopago.com.br/developers/panel/app/{seu_appId}/webhooks

mercado.pago.pos= **Pos ID** do seu caixa criado em -> 
https://www.mercadopago.com.br/stores

mercado.pago.token= **Access Token** em -> 
https://www.mercadopago.com.br/developers/panel/app/{seu_appId}/credentials/production
