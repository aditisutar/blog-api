package com.blogappapi.entities;

import com.blogappapi.payloads.CommentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @Column(length = 100,nullable = false)
    private String postTitle;
    @Column(length = 1000)
    private String postContent;
    private String postImageName;
    private Date postAddedDate;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comment=new ArrayList<>();


}
