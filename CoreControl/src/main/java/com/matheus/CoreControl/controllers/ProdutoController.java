package com.matheus.CoreControl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.matheus.CoreControl.model.Product;
import com.matheus.CoreControl.service.ProductService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "products-list";
    }

    @GetMapping("/produto/{productId}")
    public String displayPorductInfo(@RequestParam Long productId, Model model) {
        model.addAttribute("product", productService.findProductById(productId));
        return "product";
    }

    @GetMapping("/novo")
    public String showPordutctForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/salvar")
    public String saveNewProduct(@ModelAttribute Product entity) {
        productService.saveProduct(entity);
        // TODO criar registro de log para ação
        return "redirect:/produtos/";
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        // TODO criar registro de log para ação
        return "redirect:/produtos/";
    }

}
