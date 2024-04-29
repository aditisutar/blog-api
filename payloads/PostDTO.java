package com.blogappapi.payloads;

import com.blogappapi.entities.Category;
import com.blogappapi.entities.Comment;
import com.blogappapi.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int postId;
    @NotBlank
    private String postTitle;
    @NotEmpty
    private String postContent;
    private String postImageName;
    private Date postAddedDate;
    private CategoryDTO category;
    private UserDTO user;
    private List<CommentDTO> comment=new ArrayList<>();

}
