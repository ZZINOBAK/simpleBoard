package com.simpleboard.repository;

import com.simpleboard.domain.Post;
import com.simpleboard.repository.mybatis.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

//MyBatis
@Repository
@RequiredArgsConstructor
public class PostRepositoryV3 implements PostRepository {

    private final PostMapper postMapper;

    @Override
    public Post save(Post post) {
        postMapper.save(post);
        return post;
    }

    @Override
    public List<Post> findAllPost() {
        return postMapper.findAllPost();
    }

    @Override
    public Post findById(Long id) {
        return postMapper.findById(id);
    }

    @Override
    public Post updatePost(Post post) {
        postMapper.update(post);
        return post;
    }

    @Override
    public void deletePost(Long id) {
        postMapper.delete(id);
    }
}
