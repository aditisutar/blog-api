package com.blogappapi.services;

import com.blogappapi.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO, int postId);
    void deleteComment(int commentId);
}
