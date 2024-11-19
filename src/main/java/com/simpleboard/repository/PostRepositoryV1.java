package com.simpleboard.repository;

import com.simpleboard.domain.Post;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class PostRepositoryV1 implements PostRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public PostRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    //Create
    public Post save(Post post) {
        String sql = "insert into post(title, content) values(?, ?)"; // id는 제거
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            // Statement.RETURN_GENERATED_KEYS로 자동 생성된 키 반환 요청
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.executeUpdate();

            // 자동 생성된 ID 가져오기
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getLong(1)); // 데이터베이스에서 생성된 ID를 설정
                } else {
                    throw new SQLException("Creating post failed, no ID obtained.");
                }
            }
            return post;
        } catch (SQLException e) {
            throw exTranslator.translate("save", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    //Read
    public List<Post> findAllPost() {
        String sql = "select * from post";
        List<Post> posts = new ArrayList<>();
        try (
                Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
        ) {
            while (rs.next()) { // 모든 행 반복
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content").trim()); // 수정: content 값을 설정
                posts.add(post);
            }

            if (posts.isEmpty()) {
                // 예외 대신 빈 리스트를 반환
                return posts;
            }

            return posts;
        } catch (SQLException e) {
            throw exTranslator.translate("findAllPost", sql, e);
        }
    }

    public Post findById(Long id) {
        String sql = "select * from post where id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content").trim());
                return post;
            } else {
                return null;  // 데이터가 없으면 null 반환
            }
        } catch (SQLException e) {
            throw exTranslator.translate("findById", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    //Update
    public Post updatePost(Post post) {
        String sql = "update post set title=?, content=? where id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setLong(3, post.getId());
            pstmt.executeUpdate();
            return post;
        } catch (SQLException e) {
            throw exTranslator.translate("update", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    //Delete
    public void deletePost(Long id) {
        String sql = "delete from post where id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw exTranslator.translate("delete", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
//        DataSourceUtils.releaseConnection(con, dataSource);
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        return con;
    }
}
