package com.bitgo.notification.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class NotificationBody {
    @Id
    private String id;
    private int btcPrice;
    private int marketTradeVol;
    private int intraDayHighPrice;
    private int marketCap;
}
