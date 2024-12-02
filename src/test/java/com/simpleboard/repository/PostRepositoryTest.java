package com.simpleboard.repository;

import com.simpleboard.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @AfterEach
    void afterEach() {
        if (postRepository instanceof OldPostRepository) {
            ((OldPostRepository) postRepository).clearStore();
        }
    }

    @Test
    void save() {
        log.info("postRepository={}", postRepository.getClass());
        //given
        Post post = new Post("세이브제목", "세이브내용");
        //when
        Post savedPost = postRepository.save(post);
        Post findPost = postRepository.findById(post.getId());
        //then
        assertThat(findPost.getId()).isEqualTo(savedPost.getId());
    }

    @Test
    void findAllPost() {
        //given
        Post post1 = new Post("전체조회제목1", "전체조회내용1");
        Post post2 = new Post("전체조회제목2", "전체조회내용2");
        postRepository.save(post1);
        postRepository.save(post2);
        //when
        List<Post> result = postRepository.findAllPost();
        log.info("result={}", result);
        //then
        assertThat(result.size()).isEqualTo(2);
//        assertThat(result).contains(post1, post2);
        assertThat(result)
                .usingElementComparatorOnFields("title", "content")  // title과 content 필드 기준으로 비교
                .contains(post1, post2);  // 값 비교: title과 content가 일치해야 한다
    }

    @Test
    void findById() {
        //given
        Post post1 = new Post("하나조회제목1", "하나조회내용1");
        Post post2 = new Post("하나조회제목2", "하나조회내용2");
        Post post3 = new Post("하나조회제목3", "하나조회내용3");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        //when
        Post findPost = postRepository.findById(1L);

        //then
        assertThat(findPost.getTitle()).isEqualTo(post1.getTitle());
    }
    @Test
    void updatePost() {
        //given
        Post post = new Post("업데이트제목1", "업데이트내용1");
        Post seavedPost = postRepository.save(post);

        //when
        Post updateParam = new Post("업데이트제목2", "업데이트내용2");
        updateParam.setId(seavedPost.getId());
        postRepository.updatePost(updateParam);
        Post findPost = postRepository.findById(seavedPost.getId());

        //then
        assertThat(findPost.getTitle()).isEqualTo(updateParam.getTitle());
        assertThat(findPost.getContent()).isEqualTo(updateParam.getContent());
    }

    @Test
    void deletePost() {
        //given
        Post post = new Post("삭제제목", "삭제내용");
        Post savedPost = postRepository.save(post);

        //when
        postRepository.deletePost(savedPost.getId());

        //then
        // 삭제된 후 해당 id로 찾은 post는 null이어야 한다
        Post deletedPost = postRepository.findById(savedPost.getId());  // 삭제된 데이터 조회

        // 삭제된 후 null이어야 한다
        assertThat(deletedPost).isNull();  // 삭제되었으면 null이어야 한다
    }
}
