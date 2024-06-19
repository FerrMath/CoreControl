/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.matheus.CoreControl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.matheus.CoreControl.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("user") String login,
            @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response,
            Model model, HttpSession session) {

        if (userService.validateUser(login, password)) {
            session.setAttribute("user", login);
            return "redirect:/home";
        }
        model.addAttribute("error", true);
        return "/login";
    }

    @GetMapping("/logout")
    public String getMethodName(HttpSession session, HttpServletResponse response) {
        // Invalidate the session
        session.invalidate();
        return "redirect:/login";
    }
}
