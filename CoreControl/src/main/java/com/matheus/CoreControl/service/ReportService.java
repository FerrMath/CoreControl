package com.matheus.CoreControl.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.model.enums.EditType;
import com.matheus.CoreControl.model.reportEntrys.EditEntry;
import com.matheus.CoreControl.model.reportEntrys.PurchaseEntry;
import com.matheus.CoreControl.repository.ReportRepo;

@Service
public class ReportService {

    private final ReportRepo reportRepo;
    private Report currentReport;

    @Autowired
    public ReportService(ReportRepo reportRepo) {
        this.reportRepo = reportRepo;
        initializeCurrentReport();
    }

    private void initializeCurrentReport() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        this.currentReport = isCurrentReportValid(year, month) ? reportRepo.findReportByMonthAndYear(year, month)
                : new Report();
    }

    public void newEditEntry(EditType type, Long product, Long user) {
        EditEntry entry = new EditEntry(type, product, user);
        currentReport.addEntry(entry);
        reportRepo.save(currentReport);
        System.out.println("Edit entry added to report");
    }

    public void newPurchaseEntry(Long product, Long user, Double price, Double quantity) {
        PurchaseEntry entry = new PurchaseEntry(product, user, price, quantity);
        currentReport.addEntry(entry);
        reportRepo.save(currentReport);
        System.out.println("Purchase entry added to report");
    }

    public boolean isCurrentReportValid(int year, int month) {
        return reportRepo.findReportByMonthAndYear(year, month) != null;
    }
}
