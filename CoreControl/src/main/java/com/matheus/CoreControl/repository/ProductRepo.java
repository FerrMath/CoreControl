package com.matheus.CoreControl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.matheus.CoreControl.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    // Custom queries are created here
    Product findByName(String name);

    // Find all by name that has a substring
    List<Product> findAllByNameContaining(String name);

    // Find all by category
    List<Product> findAllByCategory(String category);

    // Find all by category that has a substring
    List<Product> findAllByCategoryContaining(String category);

    // Query for all products that have a stock close to minimum alowed on database
    @Query("SELECT p FROM Product p WHERE p.stock <= p.minStock*1.5 and p.stock >= p.minStock")
    List<Product> findAllByStockCloseToMinStock();

    // Query for all products that have a stock below minimum alowed on database
    @Query("SELECT p FROM Product p WHERE p.stock < p.minStock")
    List<Product> findAllByStockBelowMinStock();
}
