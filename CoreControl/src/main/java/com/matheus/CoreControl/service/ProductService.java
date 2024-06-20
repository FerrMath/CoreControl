package com.matheus.CoreControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.CoreControl.model.Product;
import com.matheus.CoreControl.repository.ProductRepo;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public void updateProduct(Product product) {
        if (product == null) {
            return;
        }
        if (productIsValid(product)) {
            productRepo.save(product);
        }
    }

    public Product findProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public List<Product> findAllProductByNameContaining(String name) {
        return productRepo.findAllByNameContaining(name);
    }

    public List<Product> findAllByCategoryContaining(String category) {
        return productRepo.findAllByCategoryContaining(category);
    }

    public List<Product> findProductsCloseToMinStock() {
        return productRepo.findAllByStockCloseToMinStock();
    }

    public List<Product> findProductsBelowMinStock() {
        return productRepo.findAllByStockBelowMinStock();
    }

    public void saveProduct(Product product) {
        if (product == null) {
            return;
        }

        if (productIsValid(product)) {
            product.setStock(0.0);
            product.setDiscount(0.0);
            productRepo.save(product);
        }
    }

    public List<Product> findProductsBelowStockMinimun() {
        return findAllProducts().stream().filter(p -> p.isStockBelowMin()).toList();
    }

    public List<Product> findProductsAlmostEmpty() {
        return findAllProducts().stream().filter(p -> p.isStockAlmostEmpty()).toList();
    }

    private boolean productIsValid(Product product) {
        return product != null && product.getName() != null && product.getPrice() != null && product.getPrice() > 0.0
                && product.getCost() != null && product.getCost() > 0.0
                && (product.getStock() == null || product.getStock() >= 0.0);
    }
}
