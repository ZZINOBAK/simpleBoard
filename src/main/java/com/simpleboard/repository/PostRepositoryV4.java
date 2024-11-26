package com.simpleboard.repository;

import com.simpleboard.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//JPA
@Repository
@RequiredArgsConstructor
@Transactional
public class PostRepositoryV4 implements PostRepository {

    private final EntityManager em;

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public List<Post> findAllPost() {
        String jpql = "select p from Post p";  // 엔티티 Post에서 모든 필드를 조회
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        return query.getResultList();
    }

    @Override
    public Post findById(Long id) {
        Post post = em.find(Post.class, id);
        return post;
    }

    @Override
    public Post updatePost(Post post) {
        Post findPost = em.find(Post.class, post.getId());
        findPost.setTitle(post.getTitle());
        findPost.setContent(post.getContent());
        return findPost;
    }

    @Override
    public void deletePost(Long id) {
        em.remove(em.find(Post.class, id));
    }
}
