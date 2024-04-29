package com.blogappapi.services.impl;

import com.blogappapi.entities.Category;
import com.blogappapi.entities.Posts;
import com.blogappapi.entities.User;
import com.blogappapi.exceptions.ResourceNotFoundException;
import com.blogappapi.payloads.PostDTO;
import com.blogappapi.payloads.PostResponse;
import com.blogappapi.repos.CategoryRepo;
import com.blogappapi.repos.PostsRepo;
import com.blogappapi.repos.UserRepo;
import com.blogappapi.services.PostServcie;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.slf4j.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostServcie {
    @Autowired
    private PostsRepo postsRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;
    Logger logger = LoggerFactory.getLogger(Controller.class);
    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
       User user=  this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
       Category category= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
       Posts post= this.modelMapper.map(postDTO, Posts.class);
       post.setPostImageName("default.png");
       post.setPostAddedDate(new Date());
       post.setUser(user);
       post.setCategory(category);
       Posts createdPosts=this.postsRepo.save(post);
       logger.info("PostDTO imageName:"+postDTO.getPostImageName());
       logger.info("Updated post: "+createdPosts.getPostImageName());
        System.out.println("PostDTO imageName:"+postDTO.getPostImageName());
        System.out.println("Updated post: "+createdPosts.getPostImageName());
       return this.modelMapper.map(createdPosts,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, int postId) {
       Posts post= this.postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
       post.setPostTitle(postDTO.getPostTitle());
       post.setPostContent(postDTO.getPostContent());
       post.setPostImageName(postDTO.getPostImageName());
       Posts updatedPost=this.postsRepo.save(post);
       return this.modelMapper.map(updatedPost,PostDTO.class);
    }

    @Override
    public PostDTO getPostById(int postId) {
        Posts post= this.postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        return this.modelMapper.map(post,PostDTO.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else {
            sort=Sort.by(sortBy).descending();
        }
        PostResponse postResponse=new PostResponse();
        Pageable pg= PageRequest.of(pageNumber,pageSize, sort);
        Page<Posts> post=this.postsRepo.findAll(pg);
        List<Posts> postContent= post.getContent();
        List<PostDTO> allPosts=  postContent .stream().map((p)->this.modelMapper.map(p,PostDTO.class)).collect(Collectors.toList());
        postResponse.setContent(allPosts);
        postResponse.setPageNumber(post.getNumber());
        postResponse.setPageSize(post.getSize());
        postResponse.setTotalElements(post.getTotalElements());
        postResponse.setTotalPages(post.getTotalPages());
        postResponse.setLastPage(post.isLast());


        return postResponse;
    }

    @Override
    public void deletePost(int postId) {
        Posts deletePost= this.postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post delete","id",postId));
        this.postsRepo.delete(deletePost);
    }

    @Override
    public List<PostDTO> getPostsByCategory(int categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
//       Pageable page= PageRequest.of(pageNumber,pageSize, category);



        List<Posts> postsByCat= this.postsRepo.findByCategory(category);

       // return postsByCat.stream().map((p)->this.modelMapper.map(p,PostDTO.class)).collect(Collectors.toList());
        return  null;
    }

    @Override
    public List<PostDTO> getPostsByUser(int userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        List<Posts> postsByUser= this.postsRepo.findByUser(user);
        return postsByUser.stream().map((p)->this.modelMapper.map(p,PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
       List<Posts> searchedPost= this.postsRepo.findBypostTitleContaining(keyword);
      List<PostDTO> post= searchedPost.stream().map((p)->this.modelMapper.map(p,PostDTO.class)).collect(Collectors.toList());
        return post;
    }
}
