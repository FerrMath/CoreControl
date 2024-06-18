package com.matheus.CoreControl.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.CoreControl.model.reportEntrys.SaleEntry;
import com.matheus.CoreControl.service.ReportService;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/new")
    public ResponseEntity<SaleEntry> newSaleEntry(
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "clientId") Long clientId,
            @RequestParam(name = "quantity") Double quantity) {

        SaleEntry entry = reportService.newSaleEntry(productId, userId, clientId, quantity);
        return ResponseEntity.ok(entry);
    }
}
