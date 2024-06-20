package com.matheus.CoreControl.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.service.ReportService;
import com.matheus.CoreControl.util.FileGenerator;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class DownloadController {

    @Autowired
    private ReportService reportService;

    private FileGenerator fileGenerator = new FileGenerator();

    @GetMapping("/download/{reportId}")
    @Transactional
    public ResponseEntity<String> download(@PathVariable("reportId") Long reportId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=example.csv");
        headers.add("Content-Type", "text/csv; charset=UTF-8");

        Report report = reportService.getReportById(reportId);
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (report.getEntries() == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String csvContent = fileGenerator.csvWritter(report.getEntries());
        if (csvContent == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
    }
}
