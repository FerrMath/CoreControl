package com.matheus.CoreControl.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.model.reportEntrys.ReportEntry;
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

    // TODO Temporary route to show the report page
    @GetMapping("/report/{reportId}")
    public String showReport(@PathVariable Long reportId, Model model) {
        Report report = reportService.getReportById(reportId);
        List<?> entries = report.getEntries();
        model.addAttribute("report", report);
        model.addAttribute("entries", entries);
        return "report";
    }

}
