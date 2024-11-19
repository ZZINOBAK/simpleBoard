package com.simpleboard.repository;

import com.simpleboard.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
//@Rollback(true)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepositoryV1 postRepository;

    @Test
    void save() {
        //given
        Post post = new Post("제목1", "내용용용용용ㅇ");
        //when
        Post savedPost = postRepository.save(post);
        Post findPost = postRepository.findById(post.getId());
        //then
        assertThat(findPost.getId()).isEqualTo(savedPost.getId());
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
//        assertThat(result).contains(post1, post2);
        assertThat(result)
                .usingElementComparatorOnFields("title", "content")  // title과 content 필드 기준으로 비교
                .contains(post1, post2);  // 값 비교: title과 content가 일치해야 한다
    }

    @Test
    void findById() {
        //given
        Post post1 = new Post("제목1", "내용용용용용ㅇ");
        Post post2 = new Post("제목2", "내용ddddd용용용용ㅇ");
        Post post3 = new Post("제목3", "내333dd용용용용ㅇ");
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
        Post post = new Post("제목1", "내용용용용용ㅇ");
        Post seavedPost = postRepository.save(post);

        //when
        Post updateParam = new Post("제목2", "내용용용용용ㅇ");
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
        Post post = new Post("제목1", "내용용용용용ㅇ");
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
