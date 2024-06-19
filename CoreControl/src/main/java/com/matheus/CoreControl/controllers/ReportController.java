package com.matheus.CoreControl.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.model.enums.EntryType;
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

    @GetMapping("/report/{reportId}")
    public String showReport(@PathVariable Long reportId, Model model) {
        Report report = reportService.getReportById(reportId);
        List<?> entries = report.getEntries();
        model.addAttribute("report", report);
        model.addAttribute("entries", entries);
        return "report";
    }

    @GetMapping("/report/{reportId}/filter")
    public String showFilteredReport(
            @PathVariable("reportId") Long reportId,
            @RequestParam(name = "sale", required = false) String sale,
            @RequestParam(name = "purchase", required = false) String purchase,
            @RequestParam(name = "edit", required = false) String edit,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "prodId", required = false) Long prodId,
            @RequestParam(name = "userId", required = false) Long userId,
            Model model) {

        // if all filters are null, return the original report
        if (sale == null && purchase == null && edit == null && startDate.isEmpty() && endDate.isEmpty()
                && prodId == null && userId == null) {
            return "redirect:/reports/report/" + reportId;
        }

        Report report = reportService.getReportById(reportId);
        List<ReportEntry> rawEntries = report.getEntries();
        List<ReportEntry> entries = new ArrayList<>();

        LocalDate start = startDate.isEmpty() ? LocalDate.now().minusMonths(3) : LocalDate.parse(startDate);
        LocalDate end = endDate.isEmpty() ? LocalDate.now().plusMonths(1)
                : LocalDate.parse(endDate);

        entries = filterEntriesByUserandProduct(rawEntries, userId, prodId);
        entries = filterEntriesByDate(entries, start, end);
        entries = filterEntriesBySelectedFilters(entries, sale, purchase, edit);
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
        LocalDate end = LocalDate.parse(endDate).equals(null) ? LocalDate.now().plusMonths(1)
                : LocalDate.parse(endDate);
        List<Report> reports = reportService.findReportsByDate(start, end);
        model.addAttribute("reports", reports);
        return "reports-list";
    }

    private List<ReportEntry> filterEntriesByUserandProduct(List<ReportEntry> entries, Long userId, Long prodId) {
        if (userId == null && prodId == null) {
            return entries;
        }
        if (userId == null) {
            return entries.stream().filter(entry -> entry.getProductId().equals(prodId)).collect(Collectors.toList());
        }

        if (prodId == null) {
            return entries.stream().filter(entry -> entry.getUserId().equals(userId)).collect(Collectors.toList());
        }

        return entries.stream().filter(entry -> entry.getUserId().equals(userId) && entry.getProductId().equals(prodId))
                .collect(Collectors.toList());
    }

    private List<ReportEntry> filterEntriesByDate(List<ReportEntry> entries, LocalDate start, LocalDate end) {
        return entries.stream()
                .filter(entry -> (entry.getDate().isAfter(start) || entry.getDate().isEqual(start))
                        && (entry.getDate().isBefore(end) || entry.getDate().isEqual(end)))
                .collect(Collectors.toList());

    }

    private List<ReportEntry> filterEntriesBySelectedFilters(List<ReportEntry> entries, String sale, String purchase,
            String edit) {
        List<ReportEntry> result = new ArrayList<>();
        for (ReportEntry entry : entries) {
            if (sale != null && entry.getType().equals(EntryType.SALE)) {
                result.add(entry);
            }
            if (purchase != null && entry.getType().equals(EntryType.PURCHASE)) {
                result.add(entry);
            }
            if (edit != null && entry.getType().equals(EntryType.EDIT)) {
                result.add(entry);
            }
        }
        return result.size() == 0 ? entries : result;
    }
}
