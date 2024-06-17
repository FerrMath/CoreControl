package com.matheus.CoreControl.model;

import java.time.LocalDate;
import java.util.List;

import com.matheus.CoreControl.model.reportEntrys.PurchaseEntry;
import com.matheus.CoreControl.model.reportEntrys.ReportEntry;
import com.matheus.CoreControl.model.reportEntrys.SaleEntry;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double totalSales = 0.0;
    @Column(nullable = false)
    private Double totalExpenses = 0.0;
    @Column(nullable = false)
    private Double totalProfit;
    private LocalDate startDate;
    private LocalDate endDate;
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntry> entries;

    public Report() {
        this.startDate = LocalDate.now();
        // Set end date to the first day of the next month
        this.endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
    }

    public void addEntry(ReportEntry entry) {
        if (entry == null)
            throw new IllegalArgumentException("Entry cannot be null");

        entries.add(entry);

        if (entry instanceof PurchaseEntry) {
            totalExpenses += ((PurchaseEntry) entry).getTotal();
        } else if (entry instanceof SaleEntry) {
            totalSales += ((SaleEntry) entry).getTotal();
        }

        updateTotalProfit();
    }

    public Double getTotalProfit() {
        return totalSales - totalExpenses;
    }

    private void updateTotalProfit() {
        this.totalProfit = getTotalSales() - getTotalExpenses();
    }
}
