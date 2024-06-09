package com.matheus.CoreControl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.matheus.CoreControl.service.ProductService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // TODO temporário
    @GetMapping("/produto/{id}")
    public String getMethodName() {
        return "product";
    }

    @GetMapping("/novo")
    public String showPordutctForm() {
        return "product-form";
    }

    // TODO Redireciona para a página inicial temporariamente
    @PostMapping("/salvar")
    public String postMethodName(@RequestBody String entity) {
        System.out.println("Salvou com sucesso o produto");
        return "redirect:/";
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/produtos/";
    }

}
