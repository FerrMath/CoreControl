/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.matheus.CoreControl.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.model.User;
import com.matheus.CoreControl.service.ProductService;
import com.matheus.CoreControl.service.ReportService;
import com.matheus.CoreControl.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SiteController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(HttpSession session, HttpServletResponse response, Model model) {
        return homeOrLogin(session, response, model);
    }

    @GetMapping("/home")
    public String homeOrLogin(HttpSession session, HttpServletResponse response, Model model) {
        Report currReport = reportService.getCurrentReport();
        List<Report> reports = reportService.findAllReports();
        List<?> entries = currReport.getEntries();
        User user = userService.findUserByLogin((String) session.getAttribute("user"));

        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("CTLproducts", productService.findProductsAlmostEmpty().size());
        model.addAttribute("OOStockMin", productService.findProductsBelowStockMinimun().size());
        model.addAttribute("currentReport", currReport);
        model.addAttribute("reports", reports);
        model.addAttribute("entries", entries);
        model.addAttribute("user", user);
        return "index";
    }
}
