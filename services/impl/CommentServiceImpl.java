package com.blogappapi.services.impl;

import com.blogappapi.entities.Comment;
import com.blogappapi.entities.Posts;
import com.blogappapi.exceptions.ResourceNotFoundException;
import com.blogappapi.payloads.CommentDTO;
import com.blogappapi.repos.CommentRepo;
import com.blogappapi.repos.PostsRepo;
import com.blogappapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostsRepo postsRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, int postId) {
        Posts post=this.postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment=this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment updatedComment=this.commentRepo.save(comment);
        return this.modelMapper.map(updatedComment,CommentDTO.class);
    }

    @Override
    public void deleteComment(int commentId) {
        this.commentRepo.deleteById(commentId);
    }
}
