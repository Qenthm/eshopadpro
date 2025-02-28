package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String listProduct(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

    // NEW: Display the update product form
    @GetMapping("/update")
    public String updateProductPage(@RequestParam(value = "productId", required = true) String productId, Model model) {

        // Try to find the product by its ID
        Product product = service.findAll().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(null);

        // Pass the product to the form
        model.addAttribute("product", product);
        return "UpdateProduct"; // The name of your update product Thymeleaf view
    }

    // Handle product updates
    @PostMapping("/update")
    public String updateProduct(@RequestParam("productId") String productId,
                                @ModelAttribute("product") Product updatedProduct, Model model) {
        // Update the product using data from the form
        service.updateProduct(
                productId,
                updatedProduct.getProductName(),
                updatedProduct.getProductQuantity()
        );

        return "redirect:list"; // Redirect to the product list after update
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        service.deleteProduct(productId);
        return "redirect:/product/list";
    }
}
