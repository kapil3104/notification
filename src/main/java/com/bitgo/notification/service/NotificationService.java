package com.bitgo.notification.service;

import com.bitgo.notification.dto.NotificationRequest;
import com.bitgo.notification.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> sendNotification(NotificationRequest notificationRequest) throws Exception;
    boolean deleteNotification(String notificationId) throws Exception;
    List<NotificationResponse> getAllNotifications() throws Exception;
}
