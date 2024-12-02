package com.simpleboard;

import com.simpleboard.repository.*;
import com.simpleboard.service.PostService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

//    private final DataSource dataSource;
//    private final PostMapper postMapper;
    private final EntityManager em;

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostRepository postRepository() {
//        return new SimplePostRepository();
//        return new LegacyJdbcPostRepository(dataSource);
//        return new JdbcTemplatePostRepository(dataSource);
//        return new MyBatisPostRepository(postMapper);
        return new JpaPostRepository(em);
    }
}
