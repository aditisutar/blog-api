package com.blogappapi.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private int categoryID;
    @NotBlank
    @Size(min = 3)
    private String categoryTitle;
    @NotEmpty
    @Size(min = 10, max = 100)
    private String categoryDescription;
}
