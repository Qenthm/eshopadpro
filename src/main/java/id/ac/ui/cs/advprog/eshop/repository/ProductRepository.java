package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class ProductRepository {
    // Using CopyOnWriteArrayList for thread-safe operations
    private List<Product> productData = new CopyOnWriteArrayList<>();
    private int currentProductId = 1;

    public Product create(Product product) {
        product.setProductId(String.valueOf(currentProductId++));
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        // Returns a snapshot of current state
        return new ArrayList<>(productData).iterator();
    }

    public Optional<Product> findById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
    }

    public void updateProduct(String productId, String newName, int newQuantity) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                product.setProductName(newName);
                product.setProductQuantity(newQuantity);
                return;
            }
        }
        throw new RuntimeException("Product with ID " + productId + " not found");
    }

    public boolean delete(String productId) {
        return productData.removeIf(product -> product.getProductId().equals(productId));
    }

    public void deleteAll() {
        productData.clear();
    }
}