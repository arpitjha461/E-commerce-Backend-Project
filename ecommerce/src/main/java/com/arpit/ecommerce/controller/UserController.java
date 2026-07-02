package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.LoginRequestDTO;
import com.arpit.ecommerce.dto.LoginResponseDTO;
import com.arpit.ecommerce.dto.UserRequestDTO;
import com.arpit.ecommerce.dto.UserResponseDTO;
import com.arpit.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public UserResponseDTO register (@Valid @RequestBody UserRequestDTO requestDTO){
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
    public UserResponseDTO userUpdate(@PathVariable Long id,@Valid @RequestBody UserRequestDTO requestDTO){
        return userService.updateUser(id, requestDTO);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        return  userService.deleteUser(id);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        System.out.println("LOGIN API HIT");
       return userService.login(loginRequestDTO);
    }
}
