package com.blogappapi.repos;

import com.blogappapi.entities.Category;
import com.blogappapi.entities.Posts;
import com.blogappapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepo extends JpaRepository<Posts, Integer> {
    List<Posts> findByUser(User user);
    List<Posts> findByCategory(Category category);
    List<Posts> findBypostTitleContaining(String title);
}
