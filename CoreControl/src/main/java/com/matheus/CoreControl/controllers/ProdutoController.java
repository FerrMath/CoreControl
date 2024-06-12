package com.matheus.CoreControl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.matheus.CoreControl.model.Product;
import com.matheus.CoreControl.service.ProductService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

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
    public String displayPorductInfo(@PathVariable Long productId, Model model) {
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
        System.out.println("Saving product: " + entity.toString());
        productService.saveProduct(entity);
        // TODO criar registro de log para ação
        return "redirect:/produtos/";
    }

    @GetMapping("/editar/{productId}")
    public String showMovieEditForm(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.findProductById(productId));
        model.addAttribute("edit", true);
        return "product-form";
    }

    @PutMapping("/editar/{productId}")
    public String submitMovieEdit(@PathVariable Long productId, @ModelAttribute Product product) {
        // TODO criar registro de log para ação
        productService.saveProduct(product);
        return "redirect:/produtos/produto/" + productId;
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        // TODO criar registro de log para ação
        return "redirect:/produtos/";
    }

}
