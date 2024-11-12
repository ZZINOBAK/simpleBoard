package com.simpleboard.repository;

import com.simpleboard.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    private static Map<Long, Post> store = new HashMap<>();
    private static Long sequence = 0L;

    public Post save(Post post) {
        post.setId(++sequence);
        store.put(post.getId(), post);
        return post;
    }

    public Post findById(Long id) {
        return store.get(id);
    }

    public List<Post> findAllPost() {
        return new ArrayList<>(store.values());
    }

    public Post updatePost(Post post) {
        store.put(post.getId(), post);
        return post;
    }

    public void delete(Long id) {
        store.remove(id);
    }
    public void clearStore() {
        store.clear();
    }

}
