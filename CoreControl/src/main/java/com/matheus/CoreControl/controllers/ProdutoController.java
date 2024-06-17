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
import com.matheus.CoreControl.model.enums.EditType;
import com.matheus.CoreControl.service.ProductService;
import com.matheus.CoreControl.service.ReportService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProductService productService;
    private final ReportService reportService;

    @Autowired
    public ProdutoController(ProductService productService, ReportService reportService) {
        this.productService = productService;
        this.reportService = reportService;
    }

    @GetMapping("/")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "products-list";
    }

    @GetMapping("/filter/")
    public String filterProducts(@RequestParam(name = "filter") String filter,
            @RequestParam(name = "value") String value,
            Model model) {
        switch (filter) {
            case "name":
                model.addAttribute("products", productService.findAllProductByNameContaining(value));
                break;
            case "category":
                model.addAttribute("products", productService.findAllByCategoryContaining(value));
                break;
            default:
                // Optional: Add a message or handle the default case differently
                model.addAttribute("message", "No matching filter found.");
                break;
        }
        return "products-list";
    }

    @GetMapping("/close-to-min-stock")
    public String listProductsCloseToMinStock(Model model) {
        model.addAttribute("products", productService.findProductsCloseToMinStock());
        return "products-list";
    }

    @GetMapping("/below-min-stock")
    public String listProductsBelowMinStock(Model model) {
        model.addAttribute("products", productService.findProductsBelowMinStock());
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
        // TODO editar para pegar o id do usuário logado
        reportService.newEditEntry(EditType.CREATE, entity.getId(), 1L);
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
        // TODO editar para pegar o id do usuário logado
        productService.saveProduct(product);
        reportService.newEditEntry(EditType.UPDATE, productId, 1L);
        return "redirect:/produtos/produto/" + productId;
    }

    @PostMapping("/produto/editar/{productId}")
    public String partialProductEdit(@RequestParam(name = "productId") Long productId,
            @RequestParam(name = "discount", required = false) Double productDiscount,
            @RequestParam(name = "stock", required = false) Double productStock,
            Model model) {

        Product product = productService.findProductById(productId);
        if (productDiscount == null && productStock == null) {
            System.out.println("No changes to be made");
            return "redirect:/produtos/produto/" + productId;
        }

        if (productDiscount != null) {
            if (productDiscount < 0 || productDiscount > 100) {
                System.out.println("Invalid discount value");
                return "redirect:/produtos/produto/" + productId;
            }
            product.setDiscount(productDiscount);
            productService.updateProduct(product);
        }

        if (productStock != null) {
            if (productStock < 0 || productStock > product.getMaxStock()
                    || productStock + product.getStock() > product.getMaxStock()) {
                System.out.println("Invalid stock value");
                return "redirect:/produtos/produto/" + productId;
            }

            product.setStock(product.getStock() + productStock);
            // TODO editar para pegar o id do usuário logado
            reportService.newPurchaseEntry(productId, 1L, productDiscount, productStock);
            productService.updateProduct(product);
        }

        return "redirect:/produtos/produto/" + productId;
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@RequestParam("productId") Long productId) {
        productService.deleteProduct(productId);
        // TODO editar para pegar o id do usuário logado
        reportService.newEditEntry(EditType.DELETE, productId, 1L);
        return "redirect:/produtos/";
    }
}
