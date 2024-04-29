package com.blogappapi.controllers;

import com.blogappapi.configurations.AppConstants;
import com.blogappapi.payloads.ApiResponse;
import com.blogappapi.payloads.PostDTO;
import com.blogappapi.payloads.PostResponse;
import com.blogappapi.services.FileService;
import com.blogappapi.services.PostServcie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostServcie postServcie;
    @Autowired
    private FileService fileService;
    Logger logger = LoggerFactory.getLogger(Controller.class);


    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable int userId, @PathVariable int categoryId){
       PostDTO createdPost= this.postServcie.createPost(postDTO,userId,categoryId);
       return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/allPosts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ){
        PostResponse allPosts= this.postServcie.getAllPosts(pageNumber,pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPosts,HttpStatus.OK);
    }

    @GetMapping("/allPosts/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int postId){
      PostDTO postById= this.postServcie.getPostById(postId);
      String comm= String.valueOf(postById.getComment());
      logger.info("Comments..........."+comm);
        return new ResponseEntity<PostDTO>(postById,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsByCategory(@PathVariable int categoryId){
        List<PostDTO> postsByCategory= this.postServcie.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postsByCategory,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getAllPostsByUser(@PathVariable int userId){
        List<PostDTO> postsByUser= this.postServcie.getPostsByUser(userId);
        return new ResponseEntity<>(postsByUser,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePostById(@PathVariable int postId){
        this.postServcie.deletePost(postId);
       return new ApiResponse("Post is deleted",true);
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable int postId){
    this.postServcie.updatePost( postDTO, postId);
    return new ResponseEntity<>(postDTO,HttpStatus.OK);
    }
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByTile(@PathVariable String keyword){
     List<PostDTO> result= this.postServcie.searchPosts(keyword);
     return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@PathVariable int postId,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        PostDTO postDTO= this.postServcie.getPostById(postId);
       String imageName= this.fileService.uploadImage(path,image);
       postDTO.setPostImageName(imageName);
       PostDTO updatedPost= this.postServcie.updatePost(postDTO,postId);
      return new ResponseEntity<>(updatedPost,HttpStatus.CREATED);
    }
    @GetMapping(value ="/posts/downloadImage/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream is = this.fileService.getResourse(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is,response.getOutputStream());
    }
}
