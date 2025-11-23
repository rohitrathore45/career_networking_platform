package com.rohit.careerNetworkingPlatform.postsService.repository;

import com.rohit.careerNetworkingPlatform.postsService.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);
}
