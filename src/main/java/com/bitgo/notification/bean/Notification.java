package com.bitgo.notification.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Notification {
    @Id
    private String id;
    private String email;
    private String bodyId;
    private long createdTime;
    private long updatedTime;
    private Status status;
}
