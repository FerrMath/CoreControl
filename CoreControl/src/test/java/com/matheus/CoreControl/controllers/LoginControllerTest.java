package com.matheus.CoreControl.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.matheus.CoreControl.service.UserService;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        Mockito.when(userService.validateUser("validUser", "validPass")).thenReturn(true);

        mockMvc.perform(post("/login")
                .param("user", "validUser")
                .param("password", "validPass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        Mockito.when(userService.validateUser("invalidUser", "invalidPass")).thenReturn(false);

        mockMvc.perform(post("/login")
                .param("user", "invalidUser")
                .param("password", "invalidPass"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", true));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
