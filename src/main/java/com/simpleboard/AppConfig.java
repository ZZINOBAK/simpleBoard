package com.simpleboard;

import com.simpleboard.repository.*;
import com.simpleboard.repository.mybatis.PostMapper;
import com.simpleboard.service.PostService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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
//        return new OldPostRepository();
//        return new PostRepositoryV1(dataSource);
//        return new PostRepositoryV2(dataSource);
//        return new PostRepositoryV3(postMapper);
        return new PostRepositoryV4(em);
    }
}
