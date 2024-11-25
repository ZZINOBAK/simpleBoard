package com.simpleboard.repository.mybatis;

import com.simpleboard.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    void save(Post post);

    List<Post> findAllPost();

    Post findById(Long id);

    void update(@Param("updateParam") Post post);

    void delete(Long id);
}
