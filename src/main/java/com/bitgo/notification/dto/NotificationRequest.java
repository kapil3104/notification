package com.bitgo.notification.dto;

import com.bitgo.notification.bean.NotificationBody;
import lombok.Data;

import java.util.List;

@Data
public class NotificationRequest {
    private List<String> emails;
    private NotificationBody body;
}
