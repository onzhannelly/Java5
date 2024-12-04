package com.example.java5.mapper;

import com.example.java5.dto.TaskDTO;
import com.example.java5.dto.UserDTO;
import com.example.java5.entity.Task;
import com.example.java5.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setFname(user.getFname());
        dto.setLname(user.getLname());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setTasks(user.getTasks().stream().map(this::toTaskDTO).collect(Collectors.toList()));
        return dto;
    }

    public TaskDTO toTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setUserId(task.getUser().getUserId());
        dto.setCategoryId(task.getCategory().getCategoryId());
        return dto;
    }
}
