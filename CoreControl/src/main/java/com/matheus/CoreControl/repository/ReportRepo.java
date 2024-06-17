package com.matheus.CoreControl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matheus.CoreControl.model.Report;
import java.util.Optional;

public interface ReportRepo extends JpaRepository<Report, Long> {
    // Custom queries are created here

    @Query("SELECT r FROM Report r WHERE YEAR(r.startDate) = :year AND MONTH(r.startDate) = :month")
    Optional<Report> findReportByMonthAndYear(@Param("year") int year, @Param("month") int month);

}
