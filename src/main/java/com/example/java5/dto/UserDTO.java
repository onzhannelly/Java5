package com.example.java5.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String fname;
    private String lname;
    private LocalDateTime createdAt;
    private List<TaskDTO> tasks;
}
