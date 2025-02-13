package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "createProduct";
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
        return "productList";
    }

    // NEW: Display the update product form
    @GetMapping("/update")
    public String updateProductPage(@RequestParam(value = "productId", required = true) String productId, Model model) {
        // Handle the case when the product ID is invalid or missing
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID is required.");
        }

        // Try to find the product by its ID
        Product product = service.findAll().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId));

        // Pass the product to the form
        model.addAttribute("product", product);
        return "updateProduct"; // The name of your update product Thymeleaf view
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

    // Optionally, add a global exception handler for better error response
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // Show a global error page if needed
    }
}
