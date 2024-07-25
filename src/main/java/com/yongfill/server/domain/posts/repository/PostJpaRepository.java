package com.yongfill.server.domain.posts.repository;

import com.yongfill.server.domain.posts.entity.Category;
import com.yongfill.server.domain.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long>, PostQueryDSLRepository {
    List<Post> findAllByCategory(Category category);

}
