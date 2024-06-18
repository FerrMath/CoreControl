package com.matheus.CoreControl.model.reportEntrys;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matheus.CoreControl.model.Report;
import com.matheus.CoreControl.model.enums.EntryType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ReportEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long userId;
    private EntryType type;
    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    @JoinColumn(name = "report_id")
    @JsonBackReference
    private Report report;

    public ReportEntry() {
    }

    public ReportEntry(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    @JsonProperty("reportId")
    public Long getReportId() {
        return report != null ? report.getId() : null;
    }

    public abstract String[] getInfo();
}
