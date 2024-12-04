package com.example.java5.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
    private List<TaskDTO> tasks;
}
