package com.matheus.CoreControl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {
    @GetMapping("/")
    public String showReportList() {
        return "reports-list";
    }
}
