package com.matheus.CoreControl.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.service.ReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("/")
    public String showReportList(Model model) {
        List<Report> reports = reportService.findAllReports();
        model.addAttribute("reports", reports);
        return "reports-list";
    }

    @GetMapping("/report/{reportId}")
    public String showReport(@PathVariable Long reportId, Model model) {
        Report report = reportService.getReportById(reportId);
        List<?> entries = report.getEntries();
        model.addAttribute("report", report);
        model.addAttribute("entries", entries);
        return "report";
    }

    @GetMapping("/filter")
    public String showFilterForm(@RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate, Model model) {

        System.out.println(startDate);
        System.out.println(endDate);
        if (startDate == null && endDate == null) {
            System.out.println("No date filter provided");
            return "redirect:/reports/";
        }
        System.out.println("Filtering reports by date");
        LocalDate start = LocalDate.parse(startDate).equals(null) ? LocalDate.now() : LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate).equals(null) ? LocalDate.now() : LocalDate.parse(endDate);
        List<Report> reports = reportService.findReportsByDate(start, end);
        model.addAttribute("reports", reports);
        return "reports-list";
    }
}
