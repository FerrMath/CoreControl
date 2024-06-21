package com.matheus.CoreControl.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.CoreControl.model.User;
import com.matheus.CoreControl.model.reportEntrys.SaleEntry;
import com.matheus.CoreControl.service.ReportService;
import com.matheus.CoreControl.service.UserService;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<SaleEntry> newSaleEntry(
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "clientId") Long clientId,
            @RequestParam(name = "quantity") Double quantity) {

        User user = userService.findUserByLogin("admin");
        if (user == null) {
            System.out.println("User not found");
            return ResponseEntity.badRequest().build();
        }
        SaleEntry entry = reportService.newSaleEntry(productId, userId, clientId, quantity);
        if (entry == null) {
            System.out.println("Error creating sale entry");
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }
}
