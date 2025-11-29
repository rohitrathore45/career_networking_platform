package com.rohit.careerNetworkingPlatform.postsService.service;

import com.rohit.careerNetworkingPlatform.postsService.client.ConnectionsServiceClient;
import com.rohit.careerNetworkingPlatform.postsService.dto.PersonDto;
import com.rohit.careerNetworkingPlatform.postsService.dto.PostCreateRequestDto;
import com.rohit.careerNetworkingPlatform.postsService.dto.PostDto;
import com.rohit.careerNetworkingPlatform.postsService.entity.Post;
import com.rohit.careerNetworkingPlatform.postsService.event.PostCreated;
import com.rohit.careerNetworkingPlatform.postsService.exception.ResourceNotFoundException;
import com.rohit.careerNetworkingPlatform.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("Creating post user with id : {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);

        List<PersonDto> personDtoList = connectionsServiceClient.getFirstDegreeConnections(userId);

        for(PersonDto person : personDtoList) {  // send notification to each connection
            PostCreated postCreated = PostCreated.builder()
                    .postId(post.getId())
                    .content(post.getContent())
                    .userId(person.getUserId())
                    .ownerUserId(userId)
                    .build();
            postCreatedKafkaTemplate.send("post_created_topic", postCreated);
        }

        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.info("Getting the post with ID : {}", postId);

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post not found with id : "+ postId));
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
