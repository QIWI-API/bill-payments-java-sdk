# Universal payments API Java SDK

[![Build Status](https://travis-ci.com/QIWI-API/bill-payments-java-sdk.svg?branch=master)](https://travis-ci.com/QIWI-API/bill-payments-java-sdk)
[![Maven Central](https://img.shields.io/maven-central/v/com.qiwi/bill-payments-java-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.qiwi%22%20AND%20a:%22bill-payments-java-sdk%22)

Java SDK модуль для внедрения единого платежного протокола эквайринга и QIWI Кошелька.

## Подключение

Установка с помощью maven:

```xml
<dependency>
    <groupId>com.qiwi</groupId>
    <artifactId>bill-payments-java-sdk</artifactId>
    <version>1.4.0</version>
</dependency>
```

## Документация

**API QIWI Касса**: https://developer.qiwi.com/ru/bill-payments

## Авторизация

Для использования SDK требуется `secretKey`, подробности в [документации](https://developer.qiwi.com/ru/bill-payments/#auth).

```java
String secretKey = "eyJ2ZXJzaW9uIjoicmVzdF92MyIsImRhdGEiOnsibWVyY2hhbnRfaWQiOjUyNjgxMiwiYXBpX3VzZXJfaWQiOjcxNjI2MTk3LCJzZWNyZXQiOiJmZjBiZmJiM2UxYzc0MjY3YjIyZDIzOGYzMDBkNDhlYjhiNTnONPININONPN090MTg5Z**********************";

BillPaymentClient client = BillPaymentClientFactory.createDefault(secretKey);
```

## Примеры

По-умолчанию пользователю доступно несколько способов оплаты. В платежной форме параметры счета передаются в открытом виде в ссылке. Далее клиенту отображается форма с выбором способа оплаты. При использовании этого способа нельзя гарантировать, что все счета выставлены мерчантом, в отличие от выставления по API.
Через API все параметры передаются в закрытом виде, так же в API поддерживаются операции выставления и отмены счетов, возврата средств по оплаченным счетам, а также проверки статуса выполнения операций.


### Платежная форма

Простой способ для интеграции. При открытии формы клиенту автоматически выставляется счет. Параметры счета передаются в открытом виде в ссылке. Далее клиенту отображается платежная форма с выбором способа оплаты. При использовании этого способа нельзя гарантировать, что все счета выставлены мерчантом, в отличие от выставления по API.

Метод `createPaymentForm` создает платежную форму. В параметрах нужно указать:
* ключ идентификации провайдера, полученный в QIWI Кассе, `publicKey`;
* идентификатор счета `billId` внутри вашей системы;
* сумму `amount`;
* адрес перехода после успешной оплаты `successUrl`.

В результате будет получена ссылка на форму оплаты, которую можно передать клиенту.
Подробнее о доступных параметрах в [документации](https://developer.qiwi.com/ru/bill-payments/#http).

```java
String publicKey = "2tbp1WQvsgQeziGY9vTLe9vDZNg7tmCymb4Lh6STQokqKrpCC6qrUUKEDZAJ7mvFnzr1yTebUiQaBLDnebLMMxL8nc6FF5zfmGQnypdXCbQJqHEJW5RJmKfj8nvgc";

MoneyAmount amount = new MoneyAmount(
        BigDecimal.valueOf(499.90),
        Currency.getInstance("RUB")
);
String billId = UUID.randomUUID().toString();
String successUrl = "https://merchant.com/payment/success?billId=893794793973";

String paymentUrl = client.createPaymentForm(new PaymentInfo(key, amount, billId, successUrl));

```

В результате:

```
https://oplata.qiwi.com/create?amount=499.90&customFields%5BapiClient%5D=java_sdk&customFields%5BapiClientVersion%5D=1.0.0&publicKey=2tbp1WQvsgQeziGY9vTLe9vDZNg7tmCymb4Lh6STQokqKrpCC6qrUUKEDZAJ11HeiD1GQX8jTnjMxLpMcSZuGZP7xbocwJicsoBAG1HyiPDJ8A8ecBKCKWu6FP5oa&billId=920dc584-ed30-4683-8251-486426768160&successUrl=https%3A%2F%2Fmerchant.com%2Fpayment%2Fsuccess%3FbillId%3D893794793973
```

### Выставление счета

Надежный способ для интеграции. Параметры передаются server2server с использованием авторизации. Метод позволяет выставить счет, при успешном выполнении запроса в ответе вернется параметр `payUrl` - ссылка для редиректа пользователя на платежную форму.

Метод `createBill` выставляет новый счет. В параметрах нужно указать:
* идентификатор счета `billId` внутри вашей системы;
* сумма счета `amount`;
* комментарий `comment`;
* срок оплаты счета `expirationDateTime` (тип `ZonedDateTime`);
* информация о пользователе `customer`;
* адрес перехода после успешной оплаты `successUrl`.

В результате будет получен ответ с данными о выставленном счете.
Подробнее о доступных параметрах в [документации](https://developer.qiwi.com/ru/bill-payments/#create).

```java
CreateBillInfo billInfo = new CreateBillInfo(
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        BigDecimal.valueOf(199.90),
                        Currency.getInstance("RUB")
                ),
                "comment",
                ZonedDateTime.now().plusDays(45),
                new Customer(
                        "example@mail.org",
                        UUID.randomUUID().toString(),
                        "79123456789"
                ),
                "http://merchant.ru/success"
        );
BillResponse response = client.createBill(billInfo);
```

Вывод:

```json
{
  "siteId": "270304",
  "billId": "81150938-dde5-45b8-ba22-df12cc6cee27",
  "amount": {
    "value": "199.90",
    "currency": "RUB"
  },
  "status": {
    "value": "WAITING",
    "changedDateTime": "2018-11-03T15:43:51.407Z"
  },
  "comment": "comment",
  "customer": {
    "email": "example@mail.org",
    "account": "040c3bb8-b207-4ecc-9ff9-90168d3bc34f",
    "phone": "79123456789"
  },
  "creationDateTime": "2018-11-03T15:43:51.407Z",
  "expirationDateTime": "2018-12-18T15:43:50.951Z",
  "payUrl": "https://oplata.qiwi.com/form/?invoice_uid=c77a9051-1467-416b-991e-c25f06c61168&successUrl=http%3A%2F%2Fmerchant.ru%2Fsuccess",
  "customFields": {
    "apiClient": "java_sdk",
    "apiClientVersion": "1.0.0"
  }
}
```

### Информация о счете

Метод `getBillInfo` возвращает информацию о счете.
В параметрах нужно указать идентификатор счета `billId` внутри вашей системы, в результате будет получен ответ со статусом счета.
Подробнее в [документации](https://developer.qiwi.com/ru/bill-payments/#invoice-status).

```java
String billId = "fcb40a23-6733-4cf3-bacf-8e425fd1fc71";

BillResponse response = client.getBillInfo(billId);
```

Ответ:

```json
{
  "siteId": "270304",
  "billId": "fcb40a23-6733-4cf3-bacf-8e425fd1fc71",
  "amount": {
    "value": "199.90",
    "currency": "RUB"
  },
  "status": {
    "value": "WAITING",
    "changedDateTime": "2018-11-03T16:03:09.062Z"
  },
  "comment": "test",
  "customer": {
    "email": "example@mail.org",
    "account": "349d5978-bccc-4e10-be7e-3ca0808237b7",
    "phone": "79123456789"
  },
  "creationDateTime": "2018-11-03T16:03:09.062Z",
  "expirationDateTime": "2018-12-18T16:03:08.668Z",
  "payUrl": "https://oplata.qiwi.com/form/?invoice_uid=b77618b4-746c-485f-8bb8-fff43ddef114",
  "customFields": {
    "apiClient": "java_sdk",
    "apiClientVersion": "1.0.0"
  }
}
```

### Отмена неоплаченного счета

Метод `cancelBill` отменяет неоплаченный счет.
В параметрах нужно указать идентификатор счета `billId` внутри вашей системы, в результате будет получен ответ с информацией о счете.
Подробнее в [документации](https://developer.qiwi.com/ru/bill-payments/#cancel).

```java
String billId = "fcb40a23-6733-4cf3-bacf-8e425fd1fc71";

BillResponse response = client.cancelBill(billId);
```

Ответ:

```json
{
  "siteId": "270304",
  "billId": "fcb40a23-6733-4cf3-bacf-8e425fd1fc71",
  "amount": {
    "value": "199.90",
    "currency": "RUB"
  },
  "status": {
    "value": "REJECTED",
    "changedDateTime": "2018-11-03T16:03:09.062Z"
  },
  "comment": "test",
  "customer": {
    "email": "example@mail.org",
    "account": "349d5978-bccc-4e10-be7e-3ca0808237b7",
    "phone": "79123456789"
  },
  "creationDateTime": "2018-11-03T16:03:09.062Z",
  "expirationDateTime": "2018-12-18T16:03:08.668Z",
  "payUrl": "https://oplata.qiwi.com/form/?invoice_uid=b77618b4-746c-485f-8bb8-fff43ddef114",
  "customFields": {
    "apiClient": "java_sdk",
    "apiClientVersion": "1.0.0"
  }
}
```

### Возврат средств

Метод `refund` производит возврат средств.
В параметрах нужно указать:
* идентификатор счета `billId`;
* идентификатор возврата `refundId` внутри вашей системы;
* сумму возврата `amount`.
Подробнее в [документации](https://developer.qiwi.com/ru/bill-payments/#refund).

```java
String billId = "fcb40a23-6733-4cf3-bacf-8e425fd1fc71";
String refundId = UUID.randomUUID().toString();
MoneyAmount amount = new MoneyAmount(
        BigDecimal.valueOf(104.90),
        Currency.getInstance("RUB")
);

RefundResponse refundResponse = client.refundBill(paidBillId, refundId, amount);
```

В результате будет получен ответ c информацией о возврате:

```json
{
  "amount": {
    "value": "104.90",
    "currency": "RUB"
  },
  "dateTime": "2018-11-03T16:11:57.8Z",
  "refundId": "3444e8ca-cf68-4dbd-92ee-f68c4bf8f29b",
  "status": "PARTIAL"
}
```

### Информация о возврате

Метод `getRefundInfo` запрашивает статус возврата, в параметрах нужно указать:
* идентификатор счета `billId`;
* идентификатор возврата `refundId` внутри вашей системы.
Подробнее в [документации](https://developer.qiwi.com/ru/bill-payments/#refund-status).

```java
String billId = "fcb40a23-6733-4cf3-bacf-8e425fd1fc71";
String refundId = "3444e8ca-cf68-4dbd-92ee-f68c4bf8f29b";

RefundResponse response = client.getRefundInfo(paidBillId, refundId);
```

В результате будет получен ответ c информацией о возврате:

```json
{
  "amount": {
    "value": "104.90",
    "currency": "RUB"
  },
  "dateTime": "2018-11-03T16:11:57.8Z",
  "refundId": "3444e8ca-cf68-4dbd-92ee-f68c4bf8f29b",
  "status": "PARTIAL"
}

```

### Вспомогательные методы

Метод `checkNotificationSignature` осуществляет проверку подписи при нотификации о новом счете от сервера уведомлений QIWI. Принимает на вход подпись из входящего запроса, объект - тело запроса и secret ключ, с помощью которого должна осуществляться подпись:

```java
String merchantSecret = "test-merchant-secret-for-signature-check";
Notification notification = new Notification(
        new Bill(
                "test",
                "test_bill",
                new MoneyAmount(
                        BigDecimal.ONE,
                        Currency.getInstance("RUB")
                ),
                BillStatus.PAID
        ),
        "3"
);
String validSignature = "07e0ebb10916d97760c196034105d010607a6c6b7d72bfa1c3451448ac484a3b";

BillPaymentsUtils.checkNotificationSignature(validSignature, notification, merchantSecret); //true
```

### Использование альтернативного HTTP-клиента

По умолчанию для отправки HTTP-запросов используется Apache `HttpClient`, но можно воспользоваться любым другим, реализовав интерфейс `WebClient`.

```java
public interface WebClient {
    ResponseData request(
            String method,
            String url,
            Optional<String> entityOpt,
            Map<String, String> headers
    );
}
```

Примером реализации является `ApacheWebClient`, принимающий Apache `HttpClient` произвольной конфигурации.

```java
BillPaymentClient client = BillPaymentClientFactory.createCustom(
                secretKey,
                new ApacheWebClient(HttpClients.createDefault())
);
```

## Требования

* **JDK 8+**

## Лицензия

[MIT](LICENSE)
