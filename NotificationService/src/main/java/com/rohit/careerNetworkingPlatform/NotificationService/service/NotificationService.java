package com.rohit.careerNetworkingPlatform.NotificationService.service;

import com.rohit.careerNetworkingPlatform.NotificationService.entity.Notification;
import com.rohit.careerNetworkingPlatform.NotificationService.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void addNotification(Notification notification) {
        log.info("Adding notification to db, content: {}", notification.getMessage());
        notification = notificationRepository.save(notification);

        // SendMailer to send mail
        // FCM
    }

}
