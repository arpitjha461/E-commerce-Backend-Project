package com.arpit.ecommerce.controller;

import com.arpit.ecommerce.dto.request.LoginRequestDTO;
import com.arpit.ecommerce.dto.response.LoginResponseDTO;
import com.arpit.ecommerce.dto.request.UserRequestDTO;
import com.arpit.ecommerce.dto.response.UserResponseDTO;
import com.arpit.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(
        name = "User Management",
        description = "APIs for user registration, login and management"
)
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    @Operation(
            summary = "Resister a new user",
            description = "create a new user account"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "User created successfully"),
            @ApiResponse(responseCode = "400",description = "Invalid registration data"),
            @ApiResponse(responseCode = "409",description = "Email already exist")
    })
    public ResponseEntity<UserResponseDTO> register (@Valid @RequestBody UserRequestDTO requestDTO){

        UserResponseDTO responseDTO = userService.register(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
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
    @Operation(
            summary = "Login User",
            description = "Authenticates the User and returns a JWT token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful; JWT token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Invalid email or password")})
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
       return userService.login(loginRequestDTO);
    }
}
