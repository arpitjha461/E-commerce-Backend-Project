package com.arpit.ecommerce.dto;

import jakarta.validation.constraints.*;

public class UserRequestDTO {
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Email(message = "invalid email address")
    @NotEmpty(message = "email can not be empty")
    private  String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5,message = "password must contain min 5 characters")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
