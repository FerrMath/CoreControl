package com.matheus.CoreControl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheus.CoreControl.model.Report;

public interface ReportRepo extends JpaRepository<Report, Long> {
    // Custom querys go here

    @Query("SELECT r FROM Report r WHERE YEAR(r.startDate) = :year AND MONTH(r.startDate) = :month")
    Report findReportByMonthAndYear(@Param("year") int year, @Param("month") int month);

    Report findReportById(Long id);
}
