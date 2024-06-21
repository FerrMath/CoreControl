package com.matheus.CoreControl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.matheus.CoreControl.model.Product;
import com.matheus.CoreControl.model.User;
import com.matheus.CoreControl.model.enums.EditType;
import com.matheus.CoreControl.model.enums.UserRole;
import com.matheus.CoreControl.service.ProductService;
import com.matheus.CoreControl.service.ReportService;
import com.matheus.CoreControl.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/produtos")
@SuppressWarnings("null")
public class ProdutoController {

    private final ProductService productService;
    private final ReportService reportService;
    private final UserService userService;

    public ProdutoController(ProductService productService, ReportService reportService, UserService userService) {
        this.productService = productService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String listProducts(Model model, HttpSession session) {
        model.addAttribute("products", productService.findAllProducts());
        return "products-list";
    }

    @GetMapping("/novo")
    public String showPordutctForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @GetMapping("/filter/")
    public String filterProducts(
            @RequestParam(name = "filter") String filter,
            @RequestParam(name = "value") String value, Model model) {

        switch (filter) {
            case "name":
                model.addAttribute("products", productService.findAllProductByNameContaining(value));
                return "products-list";
            case "category":
                model.addAttribute("products", productService.findAllProductByNameContaining(value));
                return "products-list";
            default:
                // Optional: Add a message or handle the default case differently
                return null;
        }
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
        model.addAttribute("entries", reportService.getReportByProductId(productId));
        return "product";
    }

    @PostMapping("/salvar")
    public String saveNewProduct(@ModelAttribute Product entity, Model model) {
        productService.saveProduct(entity);
        User user = (User) model.getAttribute("user");
        reportService.newEditEntry(EditType.CREATE, entity.getId(), user.getId());
        return "redirect:/produtos/";
    }

    @GetMapping("/editar/{productId}")
    public String showMovieEditForm(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productService.findProductById(productId));
        model.addAttribute("edit", true);
        return "product-form";
    }

    @PutMapping("/editar/{productId}")
    public String submitMovieEdit(@PathVariable Long productId, @ModelAttribute Product product, Model model) {
        User user = (User) model.getAttribute("user");
        Product oldProduct = productService.findProductById(productId);
        product.setStock(oldProduct.getStock());
        product.setDiscount(oldProduct.getDiscount());
        productService.updateProduct(product);
        reportService.newEditEntry(EditType.UPDATE, productId, user.getId());
        return "redirect:/produtos/produto/" + productId;
    }

    @PostMapping("/produto/editar/{productId}")
    public String partialProductEdit(@RequestParam(name = "productId") Long productId,
            @RequestParam(name = "discount", required = false) Double productDiscount,
            @RequestParam(name = "stock", required = false) Double productStock,
            Model model) {

        Product product = productService.findProductById(productId);
        User user = (User) model.getAttribute("user");

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
            reportService.newEditEntry(EditType.UPDATE, productId, user.getId());
            return "redirect:/produtos/produto/" + productId;
        }

        if (productStock != null) {
            if (productStock < 0 || productStock > product.getMaxStock()
                    || productStock + product.getStock() > product.getMaxStock()) {
                System.out.println("Invalid stock value");
                return "redirect:/produtos/produto/" + productId;
            }

            product.setStock(product.getStock() + productStock);
            reportService.newPurchaseEntry(productId, user.getId(), product.getPrice(), productStock);
            productService.updateProduct(product);
        }
        return "redirect:/produtos/produto/" + productId;
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@RequestParam("productId") Long productId, Model model) {
        productService.deleteProduct(productId);
        User user = (User) model.getAttribute("user");
        reportService.newEditEntry(EditType.DELETE, productId, user.getId());
        return "redirect:/produtos/";
    }

    @ModelAttribute("user")
    public User user(HttpSession session) {
        String userLogin = (String) session.getAttribute("user");
        return userService.findUserByLogin(userLogin);
    }

    @ModelAttribute("validUser")
    public boolean validUser(HttpSession session, Model model) {
        User user = (User) model.getAttribute("user");
        return user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.SUPERADMIN);
    }
}
