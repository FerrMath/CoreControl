package com.matheus.CoreControl.model.reportEntrys;

import java.time.LocalDate;
import java.time.LocalTime;

import com.matheus.CoreControl.model.enums.EntryType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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

    public ReportEntry() {
    }

    public ReportEntry(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }
}
