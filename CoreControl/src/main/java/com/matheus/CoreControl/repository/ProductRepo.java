package com.matheus.CoreControl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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
}
