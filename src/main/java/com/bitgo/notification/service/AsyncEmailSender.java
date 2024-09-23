package com.bitgo.notification.service;

import com.bitgo.notification.bean.Notification;
import com.bitgo.notification.bean.Status;
import com.bitgo.notification.repo.NotificationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AsyncEmailSender {

    private final NotificationRepo notificationRepo;

    @Async("asyncExecutor")
    public void sendEmail(String email, String subject, String body, String notificationId) {
        // Third party service to send emails;
        boolean isSuccess = true;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        log.info("Email sent to : {} , body : {}", email, body);
        Notification notification = notificationRepo.findById(notificationId).get();
        if(isSuccess) {
            notification.setStatus(Status.SENT);
        } else {
            notification.setStatus(Status.FAILED);
        }
        notificationRepo.save(notification);
    }
}
