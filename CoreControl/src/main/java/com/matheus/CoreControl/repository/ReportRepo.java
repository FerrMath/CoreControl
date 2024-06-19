package com.matheus.CoreControl.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheus.CoreControl.model.Report;

public interface ReportRepo extends JpaRepository<Report, Long> {
    // Custom querys go here

    @Query("SELECT r FROM Report r WHERE YEAR(r.startDate) = :year AND MONTH(r.startDate) = :month")
    Report findReportByMonthAndYear(@Param("year") int year, @Param("month") int month);

    @Query("SELECT r FROM Report r WHERE r.startDate >= :start OR r.endDate <= :end")
    List<Report> findReportsByDate(@Param("start") LocalDate start, @Param("end") LocalDate end);

    Report findReportById(Long id);
}
