package com.example.java5.service.imlp;

import com.example.java5.dto.UserDTO;
import com.example.java5.entity.User;
import com.example.java5.mapper.UserMapper;
import com.example.java5.repository.UserRepository;
import com.example.java5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void saveUser(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setLname(userDto.getLname());
        user.setFname(userDto.getFname());
        user.setEmail(userDto.getEmail());  //conversion of form data to jpa entity, here mapper won't work as userDto don't have common attributes with User.
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // before setting the password we are encrypting using Bcrypt by Spring security.

        userRepository.save(user);

    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return userMapper.toUserDTO(user);
    }

}
