package com.rohit.careerNetworkingPlatform.NotificationService.consumer;

import com.rohit.careerNetworkingPlatform.NotificationService.entity.Notification;
import com.rohit.careerNetworkingPlatform.postsService.event.PostCreated;
import com.rohit.careerNetworkingPlatform.postsService.event.PostLiked;
import com.rohit.careerNetworkingPlatform.NotificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreated(PostCreated postCreated) {
        log.info("handlePostCreated: {}", postCreated);

        String message = String
                .format("Your connection with id : %d has created this post : %s"
                        , postCreated.getOwnerUserId(), postCreated.getContent());

        Notification notification = Notification.builder()
                .message(message)
                .userId(postCreated.getUserId())
                .build();

        notificationService.addNotification(notification);
    }

    @KafkaListener(topics = "post_liked_topic")
    public void handlePostLiked(PostLiked postLiked) {
        log.info("handlePostLiked : {}", postLiked);

        String message = String
                .format("Your connection with id : %d has liked your post with id : %s"
                        , postLiked.getLikedByUserId(), postLiked.getPostId());

        Notification notification = Notification.builder()
                .message(message)
                .userId(postLiked.getOwnerUserId())
                .build();

        notificationService.addNotification(notification);
    }
}
