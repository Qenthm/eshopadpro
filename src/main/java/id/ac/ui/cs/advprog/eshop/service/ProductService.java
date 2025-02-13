package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product); // Creates a product
    List<Product> findAll(); // Retrieves all products

    // Add this method
    Product updateProduct(String productId, String newName, int newQuantity); // Updates product details
    void deleteProduct(String productId);

}