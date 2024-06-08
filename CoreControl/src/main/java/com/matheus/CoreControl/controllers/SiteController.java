/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.matheus.CoreControl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SiteController {

    @GetMapping("/")
    public String homeOrLogin(HttpSession session, HttpServletResponse response) {
        var user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "index";
    }
}
