package com.example.java5.service;

import com.example.java5.entity.Task;
import com.example.java5.entity.User;
import com.example.java5.repository.TaskRepository;
import com.example.java5.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    public Page<Task> getUserTasks(String username, Long categoryId, String status, String sortOrder, String searchQuery, int page, int size) {
        Pageable pageable =  PageRequest.of(page, size);

        if (searchQuery != null && !searchQuery.isEmpty()) {
            return taskRepository.findByUserUsernameAndTitleContainingOrUserUsernameAndDescriptionContaining(username, searchQuery, username, searchQuery, pageable);
        }

        if (sortOrder != null) {
            if (sortOrder.equals("asc")) {
                if (categoryId != null && status != null && !status.isEmpty()) {
                    return taskRepository.findByUserUsernameAndCategory_CategoryIdAndStatus(username, categoryId, status, pageable);
                } else if (categoryId != null) {
                    return taskRepository.findByUserUsernameAndCategory_CategoryId(username, categoryId, pageable);
                } else if (status != null && !status.isEmpty()) {
                    return taskRepository.findByUserUsernameAndStatus(username, status, pageable);
                } else {
                    return taskRepository.findByUserUsername(username, pageable);
                }
            } else if (sortOrder.equals("desc")) {
                if (categoryId != null && status != null && !status.isEmpty()) {
                    return taskRepository.findByUserUsernameAndCategory_CategoryIdAndStatus(username, categoryId, status, pageable);
                } else if (categoryId != null) {
                    return taskRepository.findByUserUsernameAndCategory_CategoryId(username, categoryId, pageable);
                } else if (status != null && !status.isEmpty()) {
                    return taskRepository.findByUserUsernameAndStatus(username, status, pageable);
                } else {
                    return taskRepository.findByUserUsername(username, pageable);
                }
            }
        }

        if (categoryId != null && status != null && !status.isEmpty()) {
            return taskRepository.findByUserUsernameAndCategory_CategoryIdAndStatus(username, categoryId, status, pageable);
        } else if (categoryId != null) {
            return taskRepository.findByUserUsernameAndCategory_CategoryId(username, categoryId, pageable);
        } else if (status != null && !status.isEmpty()) {
            return taskRepository.findByUserUsernameAndStatus(username, status, pageable);
        } else {
            return taskRepository.findByUserUsername(username, pageable);
        }
    }



    public void saveTask(Task task, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Сохраняем задачу, привязываем пользователя и категорию
        task.setUser(user);
        taskRepository.save(task); // Сохраняем задачу в базе данных
    }

    public Task getTaskByIdAndUsername(Long taskId, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Task task = taskRepository.findByTaskIdAndUserUsername(taskId, username);
        if (task == null) {
            throw new RuntimeException("Task not found for the user");
        }

        return task;
    }

    public void deleteTask(Long taskId, String username) {
        Task task = getTaskByIdAndUsername(taskId, username);
        if (task != null) {
            taskRepository.delete(task);
        }
    }
}
