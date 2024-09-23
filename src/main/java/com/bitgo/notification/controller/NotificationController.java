package com.bitgo.notification.controller;

import com.bitgo.notification.dto.NotificationRequest;
import com.bitgo.notification.dto.NotificationResponse;
import com.bitgo.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @PostMapping("/")
    public ResponseEntity<List<NotificationResponse>> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        try {
            return ResponseEntity.ok(notificationService.sendNotification(notificationRequest));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        try {
            return ResponseEntity.ok(notificationService.deleteNotification(id));
        } catch(Exception e) {
            return ResponseEntity.internalServerError().body(false);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        try {
            return ResponseEntity.ok(notificationService.getAllNotifications());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
