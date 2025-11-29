package com.rohit.careerNetworkingPlatform.NotificationService.repository;

import com.rohit.careerNetworkingPlatform.NotificationService.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
