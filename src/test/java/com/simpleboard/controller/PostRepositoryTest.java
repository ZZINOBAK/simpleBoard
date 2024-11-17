package com.simpleboard.controller;

import com.simpleboard.domain.Post;
import com.simpleboard.repository.PostRepositoryV1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRepositoryTest {

    PostRepositoryV1 postRepository;

    @AfterEach
//    void afterEach() {
//        postRepository.clearStore();
//    }

    @Test
    void save() {
        Post post = new Post("제목1", "내용용용용용ㅇ");
        Post savedPost = postRepository.save(post);
        Post findItem = postRepository.findById(post.getId());
        assertThat(findItem).isEqualTo(savedPost);
    }

    @Test
    void findAllPost() {
        //given
        Post post1 = new Post("제목1", "내용용용용용ㅇ");
        Post post2 = new Post("제목2", "내용ddddd용용용용ㅇ");
        postRepository.save(post1);
        postRepository.save(post2);
        //when
        List<Post> result = postRepository.findAllPost();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(post1, post2);
    }

//    @Test
//    void updateItem() {
//        //given
//        Post post = new Post("제목1", "내용용용용용ㅇ");
//        Post seavedPost = postRepository.save(post);
//        Long id = seavedPost.getId();
//        //when
//        Post updateParam = new Post("제목2", "내용용용용용ㅇ");
//        postRepository.updatePost(updateParam);
//        Post findItem = postRepository.findById(id);
//        //then
//        assertThat(findItem.getTitle()).isEqualTo(updateParam.getTitle());
//        assertThat(findItem.getContent()).isEqualTo(updateParam.getContent());
//    }
}
