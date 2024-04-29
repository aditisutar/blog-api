package com.blogappapi.controllers;

import com.blogappapi.payloads.ApiResponse;
import com.blogappapi.payloads.CommentDTO;
import com.blogappapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable int postId){
       CommentDTO createdCommentDTO= this.commentService.createComment(commentDTO,postId);
       return new ResponseEntity<CommentDTO>(createdCommentDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/deleteComment/{commentId}")
    public ApiResponse deleteComment(@PathVariable int commentId){
        this.commentService.deleteComment(commentId);
        return new ApiResponse("Comment deleted!!", true);
    }
}
