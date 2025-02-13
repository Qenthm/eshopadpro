package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();
    private int currentProductId = 1;

    public Product create(Product product) {
        // Set unique product ID
        product.setProductId(String.valueOf(currentProductId++)); // Assign current ID and increment
        productData.add(product); // Add the product to the repository
        return product;
    }


    public Iterator<Product> findAll() {
        return productData.iterator(); // Return an iterator of all products
    }

    public Optional<Product> findById(String productId) {
        // Find a product by its ID
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
    }

    public void updateProduct(String productId, String newName, int newQuantity) {
        // Find the product by ID and update its fields
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                product.setProductName(newName);
                product.setProductQuantity(newQuantity);
                return;
            }
        }
        throw new RuntimeException("Product with ID " + productId + " not found");
    }
}