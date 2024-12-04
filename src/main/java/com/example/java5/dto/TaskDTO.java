package com.example.java5.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private Long taskId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
    private String priority;
    private Long userId; // ссылка на пользователя
    private Long categoryId; // ссылка на категорию
}


