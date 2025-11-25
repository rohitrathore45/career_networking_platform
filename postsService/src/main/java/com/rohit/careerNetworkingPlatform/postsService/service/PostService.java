package com.rohit.careerNetworkingPlatform.postsService.service;

import com.rohit.careerNetworkingPlatform.postsService.auth.AuthContextHolder;
import com.rohit.careerNetworkingPlatform.postsService.client.ConnectionsServiceClient;
import com.rohit.careerNetworkingPlatform.postsService.dto.PersonDto;
import com.rohit.careerNetworkingPlatform.postsService.dto.PostCreateRequestDto;
import com.rohit.careerNetworkingPlatform.postsService.dto.PostDto;
import com.rohit.careerNetworkingPlatform.postsService.entity.Post;
import com.rohit.careerNetworkingPlatform.postsService.exception.ResourceNotFoundException;
import com.rohit.careerNetworkingPlatform.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsServiceClient connectionsServiceClient;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("Creating post user with id : {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.info("Getting the post with ID : {}", postId);

        Long userId = AuthContextHolder.getCurrentUserId();

        // TODO: Remove in future
        // Call the connection service from post service and pass the userId inside the header

        List<PersonDto> personDtoList = connectionsServiceClient.getFirstDegreeConnections(userId);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id : "+ postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostOfUser(Long userId) {
        log.info("Getting all the post with user id : {}", userId);
        List<Post> postList = postRepository.findByUserId(userId);
        return postList
                .stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .collect(Collectors.toList());
    }

}
