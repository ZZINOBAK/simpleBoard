package com.simpleboard.service;

import com.simpleboard.domain.Post;
import com.simpleboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //Create
    public Post save(String title, String content) {
        log.info("postRepository={}", postRepository.getClass());
        Post post = new Post(title, content);
        post = postRepository.save(post);
        return post;
    }

    //Read
    public List<Post> findAllPost() {
        List<Post> posts = postRepository.findAllPost();
        return posts;
    }

    public Post findById(Long id) {
        Post post = postRepository.findById(id);
        return post;
    }

    //Update
    public Post updatePost(String title, String content, Long id) {
        Post post = findById(id);  // 수정할 게시글 찾기
        post.setTitle(title);   // 제목 수정
        post.setContent(content);  // 내용 수정
        post = postRepository.updatePost(post);
        return post;
    }

    //Delete
    public void deletePost(Long id) {
        postRepository.deletePost(id);  // ID로 게시글을 찾음
    }
}
