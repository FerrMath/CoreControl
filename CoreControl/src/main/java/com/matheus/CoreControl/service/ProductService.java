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

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public Product findProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product findProductByName(String name) {
        return productRepo.findByName(name);
    }

    public void saveProduct(Product product) {
        if (product == null) {
            System.out.println("Product is null");
            return;
        }

        if (productIsValid(product)) {
            product.setStock(0.0);
            productRepo.save(product);
        }
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    private boolean productIsValid(Product product) {
        return product != null && product.getName() != null && product.getPrice() != null && product.getPrice() > 0.0
                && product.getCost() != null && product.getCost() > 0.0 && product.getStock() == null;
    }
}