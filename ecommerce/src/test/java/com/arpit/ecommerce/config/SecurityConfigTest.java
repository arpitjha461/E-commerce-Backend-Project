package com.arpit.ecommerce.config;

import com.arpit.ecommerce.controller.UserController;
import com.arpit.ecommerce.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void invalidRegistrationReturnsBadRequestInsteadOfForbidden() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": " ",
                                  "email": "test4@gmail.com",
                                  "password": "test4@123"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
