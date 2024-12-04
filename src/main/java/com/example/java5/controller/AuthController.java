package com.example.java5.controller;

import jakarta.validation.Valid;
import com.example.java5.dto.UserDTO;
import com.example.java5.entity.User;
import com.example.java5.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){

        this.userService=userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){

        UserDTO userDto = new UserDTO();
        model.addAttribute("user",userDto); //model object is used to store data that is entered from form.
        return "register";

    }


    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto, BindingResult result, Model model) {
        System.out.println("UserDto: " + userDto); // Добавьте вывод в консоль

        User existingUser = userService.findByUsername(userDto.getUsername());
        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            result.rejectValue("username", null, "An account with this username already exists");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName());
        return "task-list";
    }
}
