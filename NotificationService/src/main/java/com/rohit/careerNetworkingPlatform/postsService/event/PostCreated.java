package com.rohit.careerNetworkingPlatform.postsService.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PostCreated {
    private Long ownerUserId;
    private Long postId;
    private Long userId;
    private String content;
}
