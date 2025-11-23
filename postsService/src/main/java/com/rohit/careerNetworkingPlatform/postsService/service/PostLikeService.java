package com.rohit.careerNetworkingPlatform.postsService.service;

import com.rohit.careerNetworkingPlatform.postsService.entity.PostLike;
import com.rohit.careerNetworkingPlatform.postsService.exception.BadRequestException;
import com.rohit.careerNetworkingPlatform.postsService.exception.ResourceNotFoundException;
import com.rohit.careerNetworkingPlatform.postsService.repository.PostLikeRepository;
import com.rohit.careerNetworkingPlatform.postsService.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void likePost(Long postId) {
        Long userId = 1L;
        log.info("User with id : {} Liking a post with id : {}", userId, postId);

        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id : " + postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (hasAlreadyLiked) throw new BadRequestException("You cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);

        // TODO : send notification to the owner of the post

    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L;
        log.info("User with id : {} Unliking a post with id : {}", userId, postId);

        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id : " + postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (!hasAlreadyLiked) throw new BadRequestException("You cannot unlike the post that you have not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }
}
