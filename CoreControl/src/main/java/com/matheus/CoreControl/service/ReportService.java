package com.matheus.CoreControl.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matheus.CoreControl.model.Product;
import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.model.enums.EditType;
import com.matheus.CoreControl.model.reportEntrys.EditEntry;
import com.matheus.CoreControl.model.reportEntrys.PurchaseEntry;
import com.matheus.CoreControl.model.reportEntrys.ReportEntry;
import com.matheus.CoreControl.model.reportEntrys.SaleEntry;
import com.matheus.CoreControl.repository.ReportRepo;

@Service
public class ReportService {

    private final ReportRepo reportRepo;
    private final ProductService productService;
    private Report currentReport;

    public ReportService(ReportRepo reportRepo, ProductService productService) {
        this.productService = productService;
        this.reportRepo = reportRepo;
        initializeCurrentReport();
    }

    public Report getReportById(Long id) {
        return reportRepo.findReportById(id);
    }

    public List<?> getReportByProductId(Long id) {
        return currentReport.getEntries().stream().filter(entry -> entry.getProductId().equals(id)).toList();
    }

    public List<Report> findAllReports() {
        return reportRepo.findAll();
    }

    private void initializeCurrentReport() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        this.currentReport = isCurrentReportValid(year, month) ? reportRepo.findReportByMonthAndYear(year, month)
                : new Report();
    }

    public Report getCurrentReport() {
        return currentReport;
    }

    public EditEntry newEditEntry(EditType type, Long product, Long user) {
        EditEntry entry = new EditEntry(type, product, user);
        currentReport.addEntry(entry);
        reportRepo.save(currentReport);
        System.out.println("Edit entry added to report");
        return entry;
    }

    public PurchaseEntry newPurchaseEntry(Long product, Long user, Double price, Double quantity) {
        PurchaseEntry entry = new PurchaseEntry(product, user, price, quantity);
        currentReport.addEntry(entry);
        reportRepo.save(currentReport);
        System.out.println("Purchase entry added to report");
        return entry;
    }

    public SaleEntry newSaleEntry(Long productId, Long user, Long client, Double quantity) {
        Product product = productService.findProductById(productId);
        if (!isValidSale(product, quantity)) {
            System.out.println("Invalid sale");
            return null;
        }
        SaleEntry entry = new SaleEntry(productId, user, client, product.getPrice(), quantity);
        product.setStock(product.getStock() - quantity);
        currentReport.addEntry(entry);
        productService.updateProduct(product);
        reportRepo.save(currentReport);
        System.out.println("Sale entry added to report");
        return entry;
    }

    public boolean isCurrentReportValid(int year, int month) {
        return reportRepo.findReportByMonthAndYear(year, month) != null;
    }

    private boolean isValidSale(Product product, Double quantity) {
        return product != null && product.getStock() >= quantity;
    }
}
