package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

@Getter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.productName = productName;
    }

    public void setProductQuantity(int productQuantity) {
        if (productQuantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
        this.productQuantity = productQuantity;
    }
}