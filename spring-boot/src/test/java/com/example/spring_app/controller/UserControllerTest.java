package com.example.spring_app.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.spring_app.model.User;
import com.example.spring_app.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;

@WebMvcTest(UserController.class)
@AutoConfigureRestTestClient
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void shouldVerifyStatusBodyAndHeaders() throws Exception {

        List<User> mockUsers = List.of(
                new User(0l, "Cristi", "Czika", "cristi", "cristiczika@gmail.com", "cristi123", "ADMIN"),
                new User(1l, "Andrei", "Preda", "andrei", "andrei@gmail.com", "andrei123", "USER"),
                new User(2l, "Maria", "Ioana", "maria", "mariaioana@gmail.com", "maria123", "USER")
        );

        Mockito.when(userService.getUsersByRole(Mockito.any())).thenReturn(mockUsers);

        mockMvc.perform(get("/api/v1/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", "3"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].username").value("cristi"))
                .andExpect(jsonPath("$[0].email").value("cristiczika@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(1))
                .andExpect(jsonPath("$[1].username").value("andrei"))
                .andExpect(jsonPath("$[1].email").value("andrei@gmail.com"))
                .andExpect(jsonPath("$[2].id").value(2))
                .andExpect(jsonPath("$[2].username").value("maria"))
                .andExpect(jsonPath("$[2].email").value("mariaioana@gmail.com"));

    }

    @Test
    public void shouldAcceptValidQueryParams() throws Exception {

        List<User> mockUsers = List.of(
                new User(0l, "Cristi", "Czika", "cristi", "cristiczika@gmail.com", "cristi123", "ADMIN"),
                new User(1l, "Andrei", "Preda", "andrei", "andrei@gmail.com", "andrei123", "USER"),
                new User(2l, "Maria", "Ioana", "maria", "mariaioana@gmail.com", "maria123", "USER")
        );

        Mockito.when(userService.getUsersByRole(Mockito.any())).thenReturn(mockUsers);

        mockMvc.perform(get("/api/v1/users")
                .param("role", "ADMIN"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestForInvalidQueryParams() throws Exception {
        ServletException exception1 = assertThrows(ServletException.class, () -> {
            mockMvc.perform(
                    get("/api/v1/users")
                            .param("role", "AB")
            );
        });

        assertTrue(exception1.getCause() instanceof ConstraintViolationException);
        assertTrue(exception1.getCause().getMessage().contains("Role must be between 3 and 15 characters"));
    }

}
