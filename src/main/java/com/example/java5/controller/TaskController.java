package com.example.java5.controller;

import com.example.java5.entity.Category;
import com.example.java5.entity.Task;
import com.example.java5.entity.User;
import com.example.java5.service.CategoryService;
import com.example.java5.service.TaskService;
import com.example.java5.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;
    private final UserService userService;

    public TaskController(TaskService taskService, CategoryService categoryService, UserService userService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    // Получение всех задач текущего пользователя
    @GetMapping
    public String listTasks(Model model, Authentication authentication,
                            @RequestParam(required = false) Long category,
                            @RequestParam(required = false) String status,
                            @RequestParam(required = false) String sortOrder,
                            @RequestParam(required = false) String searchQuery,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "2") int size) {

        String username = authentication.getName();

        // Получаем задачи с пагинацией и фильтрацией
        Page<Task> taskPage = taskService.getUserTasks(username, category, status, sortOrder, searchQuery, page, size);

        // Добавляем в модель
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("page", taskPage);
        model.addAttribute("searchQuery", searchQuery);  // Добавляем поисковый запрос для отображения в форме поиска
        model.addAttribute("lastPage", taskPage.getTotalPages() - 1);  // Добавляем последнюю страницу

        return "task-list";
    }


    // Показ формы для создания новой задачи
    @GetMapping("/create")
    public String createTaskForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Task task = new Task();
        task.setUser(user);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("task", new Task()); // или существующий объект задачи

        return "task-form";
    }

    // Сохранение задачи (создание/редактирование)
    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task, Authentication authentication) {
        // Присваиваем пользователю задачу
        String username = authentication.getName();
        User user = userService.findByUsername(username);  // Найти пользователя по имени

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        task.setUser(user);  // Привязываем задачу к пользователю

        // Пример: если категория не передана в запросе, присваиваем категорию по умолчанию
        if (task.getCategory() == null) {
            Category defaultCategory = categoryService.findById(1L);  // Пример: категория с ID 1
            task.setCategory(defaultCategory);  // Привязываем категорию к задаче
        }

        taskService.saveTask(task, username);  // Сохраняем задачу с автоматическим генерированием taskId
        return "redirect:/tasks";
    }
    // Показ формы для редактирования задачи
    @GetMapping("/edit/{taskId}")
    public String editTaskForm(@PathVariable Long taskId, Model model, Authentication authentication) {
        String username = authentication.getName();
        Task task = taskService.getTaskByIdAndUsername(taskId, username);
        if (task == null) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.findAll());
        return "task-form";
    }

    // Удаление задачи
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId, Authentication authentication) {
        String username = authentication.getName();
        taskService.deleteTask(taskId, username);
        return "redirect:/tasks";
    }
}

