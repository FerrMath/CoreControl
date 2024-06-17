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
import com.matheus.CoreControl.service.ProductService;
import com.matheus.CoreControl.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SiteController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public String homeOrLogin(HttpSession session, HttpServletResponse response, Model model) {
        var user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Report currReport = reportService.getCurrentReport();
        List<Report> reports = reportService.findAllReports();
        List<?> entries = currReport.getEntries();

        model.addAttribute("CTLproducts", productService.findProductsAlmostEmpty().size());
        model.addAttribute("OOStockMin", productService.findProductsBelowStockMinimun().size());
        model.addAttribute("currentReport", currReport);
        model.addAttribute("reports", reports);
        model.addAttribute("entries", entries);
        return "index";
    }
}
