package com.bitgo.notification.repo;

import com.bitgo.notification.bean.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepo extends MongoRepository<Notification, String> {
}
