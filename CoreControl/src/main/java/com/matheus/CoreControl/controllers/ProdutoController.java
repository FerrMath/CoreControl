package com.matheus.CoreControl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @GetMapping("/novo")
    public String showPordutctForm() {
        return "product-form";
    }

    @PostMapping("/salvar")
    public String postMethodName(@RequestBody String entity) {
        System.out.println("Salvou com sucesso o produto");
        return "redirect:/";
    }

}
