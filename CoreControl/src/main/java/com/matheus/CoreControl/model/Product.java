package com.matheus.CoreControl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double minStock;
    private Double maxStock;
    private Double cost;
    private Double profit;
    private Double price;
    private String unitMeasure;
    private Double unit;
    private String description;
    private String category;
    private Double stock;
    private Double discount;

    boolean isStockBelowMin() {
        return stock < minStock;
    }

    boolean isStockAlmostEmpty() {
        return stock < minStock * 1.5;
    }

    boolean isStockAboveMax() {
        return stock > maxStock;
    }

    public Double getPrice() {
        double effectiveCost = (cost != null) ? cost : 0.0;
        double effectiveProfit = (profit != null) ? profit : 0.0;
        double effectiveDiscount = (discount != null) ? discount : 0.0;

        return effectiveCost + (effectiveCost * effectiveProfit / 100) - (effectiveCost * effectiveDiscount / 100);
    }
}
