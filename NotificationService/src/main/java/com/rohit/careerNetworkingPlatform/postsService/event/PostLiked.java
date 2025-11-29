package com.rohit.careerNetworkingPlatform.postsService.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PostLiked {

    private Long postId;
    private Long ownerUserId;
    private Long likedByUserId;
}
