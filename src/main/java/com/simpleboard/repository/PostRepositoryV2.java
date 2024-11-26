package com.simpleboard.repository;

import com.simpleboard.domain.Post;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

//JDBC 템플릿
@Repository
public class PostRepositoryV2 implements PostRepository {

    private final JdbcTemplate template;

    public PostRepositoryV2(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    //Create
    public Post save(Post post) {
        String sql = "insert into post(title, content) values(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // id를 제외한 값만 전달하고, 자동으로 생성된 id를 keyHolder에 저장
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            return ps;
        }, keyHolder);

        // 자동 생성된 id 값을 post 객체에 설정
        post.setId(keyHolder.getKey().longValue());
        return post;
    }

    //Read
    @Override
    public List<Post> findAllPost() {
        String sql = "select * from post";
        return template.query(sql, postRowMapper());
    }

    @Override
    public Post findById(Long id) {
        String sql = "select * from post where id = ?";
        try {
            return template.queryForObject(sql, postRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;  // 결과가 없으면 null 반환
        }
    }

    //Update
    @Override
    public Post updatePost(Post post) {
        String sql = "update post set title=?, content=? where id=?";
        template.update(sql, post.getTitle(), post.getContent(), post.getId());
        return post;
    }

    //Delete
    @Override
    public void deletePost(Long id) {
        String sql = "delete from post where id=?";
        template.update(sql, id);
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post psot = new Post();
            psot.setId(rs.getLong("id"));
            psot.setTitle(rs.getString("title"));
            psot.setContent(rs.getString("content"));
            return psot;
        };
    }
}
