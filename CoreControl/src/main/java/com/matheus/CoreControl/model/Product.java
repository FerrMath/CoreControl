package com.matheus.CoreControl.model;

import jakarta.persistence.Column;
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
    private Double minStock = 0.0;
    private Double maxStock = Double.MAX_VALUE; // Assuming a default value to avoid null
    private Double cost = 0.0;
    private Double profit = 0.0;
    @Column(nullable = false)
    private Double price = 0.0;
    private String unitMeasure;
    private Double unit = 0.0;
    private String description;
    private String category;
    private Double stock = 0.0;
    private Double discount = 0.0;

    public Product() {
    }

    public Product(Long id, String name, Double price, Double stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Double getPrice() {
        double effectiveCost = (cost != null) ? cost : 0.0;
        double effectiveProfit = (profit != null) ? profit : 0.0;
        double effectiveDiscount = (discount != null) ? discount : 0.0;

        return effectiveCost + (effectiveCost * effectiveProfit / 100) - (effectiveCost * effectiveDiscount / 100);
    }

    public boolean isStockBelowMin() {
        return stock < minStock;
    }

    public boolean isStockAlmostEmpty() {
        if (isStockBelowMin()) {
            return false;
        }
        return stock < minStock * 1.5;
    }

    public boolean isStockAboveMax() {
        return stock > maxStock;
    }
}
