package com.matheus.CoreControl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @GetMapping("/")
    public String listProducts() {
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

}
