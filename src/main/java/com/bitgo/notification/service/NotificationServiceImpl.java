package com.bitgo.notification.service;

import com.bitgo.notification.bean.Notification;
import com.bitgo.notification.bean.NotificationBody;
import com.bitgo.notification.bean.Status;
import com.bitgo.notification.dto.NotificationRequest;
import com.bitgo.notification.dto.NotificationResponse;
import com.bitgo.notification.repo.NotificationBodyRepo;
import com.bitgo.notification.repo.NotificationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final NotificationBodyRepo notificationBodyRepo;
    private final AsyncEmailSender emailSender;

    @Override
    public List<NotificationResponse> sendNotification(NotificationRequest notificationRequest) throws Exception {
        List<NotificationResponse> responses = new ArrayList<>();
        if(nonNull(notificationRequest) && nonNull(notificationRequest.getBody())) {
            String notificationBodyId = saveNotificationBody(notificationRequest.getBody());
            String emailBody = prepareEmailBody(notificationRequest.getBody());
            for(String email : notificationRequest.getEmails()) {
                Notification notification = new Notification();
                notification.setEmail(email);
                notification.setStatus(Status.OUTSTANDING);
                notification.setBodyId(notificationBodyId);
                notification.setCreatedTime(System.currentTimeMillis());
                notification.setUpdatedTime(System.currentTimeMillis());
                notification = notificationRepo.save(notification);
                emailSender.sendEmail(email, "Notification from BITGO", emailBody, notification.getId());

                prepareResponse(notificationRequest, email, notification, responses);
            }
        } else {
            log.error("Validation failed ");
            throw new Exception("Validation failed");
        }
        return responses;
    }

    @Override
    public boolean deleteNotification(String notificationId) throws Exception {
        if(notificationRepo.existsById(notificationId)) {
            notificationRepo.deleteById(notificationId);
        } else {
            log.error("Notificatino not found with id");
            throw new Exception("VNotificatino not found with id");
        }
        return true;
    }

    @Override
    public List<NotificationResponse> getAllNotifications() throws Exception {
        List<Notification> notifications = notificationRepo.findAll();
        NotificationBody notificationBody = notificationBodyRepo.findById(notifications.get(0).getBodyId()).orElse(null);
        List<NotificationResponse> responses = new ArrayList<>();
        for(Notification notification : notifications) {
            NotificationResponse notificationResponse = new NotificationResponse();
            notificationResponse.setId(notification.getId());
            notificationResponse.setStatus(notification.getStatus());
            notificationResponse.setUpdatedTime(notification.getUpdatedTime());
            notificationResponse.setCreatedTime(notification.getCreatedTime());
            notificationResponse.setEmail(notification.getEmail());
            notificationResponse.setBody(notificationBody);
            responses.add(notificationResponse);
        }
        return responses;
    }

    private String saveNotificationBody(NotificationBody notificationBody) {
        return notificationBodyRepo.save(notificationBody).getId();
    }

    private String prepareEmailBody(NotificationBody notificationBody) {
        StringBuilder sb = new StringBuilder();
        sb.append(notificationBody.getBtcPrice());
        sb.append(notificationBody.getMarketTradeVol());
        sb.append(notificationBody.getIntraDayHighPrice());
        sb.append(notificationBody.getMarketCap());
        return sb.toString();
    }

    private void prepareResponse(NotificationRequest notificationRequest, String email, Notification notification, List<NotificationResponse> responses) {
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus(notification.getStatus());
        notificationResponse.setEmail(email);
        notificationResponse.setId(notification.getId());
        notificationResponse.setBody(notificationRequest.getBody());
        notificationResponse.setCreatedTime(notification.getCreatedTime());
        notificationResponse.setUpdatedTime(notification.getUpdatedTime());
        responses.add(notificationResponse);
    }
}
