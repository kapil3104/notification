package com.bitgo.notification.dto;

import com.bitgo.notification.bean.NotificationBody;
import com.bitgo.notification.bean.Status;
import lombok.Data;

@Data
public class NotificationResponse {
    private String id;
    private String email;
    private NotificationBody body;
    private long createdTime;
    private long updatedTime;
    private Status status;
}
