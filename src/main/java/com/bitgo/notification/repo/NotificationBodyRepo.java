package com.bitgo.notification.repo;

import com.bitgo.notification.bean.NotificationBody;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationBodyRepo extends MongoRepository<NotificationBody, String> {
}
