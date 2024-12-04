package com.example.java5.service;

import com.example.java5.dto.UserDTO;
import com.example.java5.entity.User;


public interface UserService {
    void saveUser(UserDTO userDto);


    User findByUsername(String username);

}