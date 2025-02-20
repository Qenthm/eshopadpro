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
    private List<Product> productData = new CopyOnWriteArrayList<>();
    private int currentProductId = 1;

    public Product create(Product product) {
        validateProductName(product.getProductName());
        validateProductQuantity(product.getProductQuantity());
        product.setProductId(String.valueOf(currentProductId++));
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return new ArrayList<>(productData).iterator();
    }

    public Optional<Product> findById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
    }

    public void updateProduct(String productId, String newName, int newQuantity) {
        validateProductName(newName);
        validateProductQuantity(newQuantity);

        Optional<Product> productOptional = findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            setProductName(product, newName);
            setProductQuantity(product, newQuantity);
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
    }

    public void setProductQuantity(Product product, int quantity) {
        validateProductQuantity(quantity);
        product.setProductQuantity(quantity);
    }

    public void setProductName(Product product, String name) {
        validateProductName(name);
        product.setProductName(name);
    }

    public void validateProductName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
    }

    private void validateProductQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
    }

    public boolean delete(String productId) {
        return productData.removeIf(product -> product.getProductId().equals(productId));
    }

    public void deleteAll() {
        productData.clear();
    }
}