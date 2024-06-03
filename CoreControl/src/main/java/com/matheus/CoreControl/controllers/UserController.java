package com.matheus.CoreControl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    // TODO temp user info page
    @GetMapping("/user/{id}")
    public String getMethodName() {
        return "user";
    }

}
