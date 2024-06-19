package com.matheus.CoreControl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheus.CoreControl.model.reportEntrys.SaleEntry;

public interface SaleEntryRepo extends JpaRepository<SaleEntry, Long> {
}
