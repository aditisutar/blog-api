package com.blogappapi.payloads;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int id;
    @NotNull(message = "Name field cannot be NULL!")
    @NotEmpty(message = "Name field cannot be empty")
    @Size(min = 4, message = "Minimum 3 chars required")
    private String name;

    @Email(message = "Enter correct mail address!!")
    @NotEmpty(message = "Mail address field cannot be empty or null")
    private String email;

    @NotEmpty(message = "Password field cannot be empty")
    @Size(min = 8, max = 16,message = "password must be between 8-16 chars")
    private String password;

    @NotNull(message = "About field cannot be NULL!")
    @NotEmpty(message = "About field cannot be empty")
    private String about;
}
