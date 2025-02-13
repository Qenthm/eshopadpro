package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.create(product); // Save a new product
    }

    @Override
    public List<Product> findAll() {
        // Convert the iterator returned by findAll to a List
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEachRemaining(products::add);
        return products;
    }

    @Override
    public Product updateProduct(String productId, String newName, int newQuantity) {
        // Update the product's details in the repository
        productRepository.updateProduct(productId, newName, newQuantity);

        // Retrieve and return the updated product
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Failed to find updated product"));
    }

    @Override
    public void deleteProduct(String productId) {
        boolean deleted = productRepository.delete(productId);
        if (!deleted) {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }
    }


}