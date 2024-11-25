package com.simpleboard.repository;

import com.simpleboard.domain.Post;

import java.util.List;

public interface PostRepository {
    public Post save(Post post);

    //Read
    public List<Post> findAllPost();

    public Post findById(Long id);

    //Update
    public Post updatePost(Post post);

    //Delete
    public void deletePost(Long id);
}
