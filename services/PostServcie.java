package com.blogappapi.services;

import com.blogappapi.entities.Posts;
import com.blogappapi.payloads.PostDTO;
import com.blogappapi.payloads.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostServcie {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    //PostDTO createDuplicatePost(PostDTO postDTO, Integer userId, Integer categoryId,String path, MultipartFile file) throws IOException;
    PostDTO updatePost(PostDTO postDTO, int postId);
    PostDTO getPostById(int postId);
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);
    void deletePost(int postId);
    List<PostDTO> getPostsByCategory(int categoryId);
    List<PostDTO> getPostsByUser(int userId);
    List<PostDTO> searchPosts(String keyword);

}
