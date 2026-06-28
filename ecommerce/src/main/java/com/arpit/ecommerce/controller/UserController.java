package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.UserRequestDTO;
import com.arpit.ecommerce.dto.UserResponseDTO;
import com.arpit.ecommerce.entity.User;
import com.arpit.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
//    public User register(@RequestBody User user)
    public UserResponseDTO register (@RequestBody UserRequestDTO requestDTO){
        return userService.register(requestDTO);
    }
    @GetMapping
    public List<UserResponseDTO> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User userUpdate(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        return  userService.deleteUser(id);
    }
}
