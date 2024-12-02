package com.simpleboard.repository;

import com.simpleboard.domain.Post;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//JDBC 인터페이스
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
        String sql = "SELECT * FROM post";
        List<Post> posts = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 커넥션, PreparedStatement 및 ResultSet을 생성
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) { // 모든 행 반복
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content").trim()); // 수정: content 값 설정
                posts.add(post);
            }
            return posts; // 게시물이 없으면 빈 리스트를 반환

        } catch (SQLException e) {
            // 예외 처리
            throw exTranslator.translate("findAllPost", sql, e);
        } finally {
            close(con, pstmt, rs);
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
//        JdbcUtils.closeConnection(con);
        //주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    private Connection getConnection() throws SQLException {
//        Connection con = dataSource.getConnection();
        //주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        Connection con = DataSourceUtils.getConnection(dataSource);
        return con;
    }
}
