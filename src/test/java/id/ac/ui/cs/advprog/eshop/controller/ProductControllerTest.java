package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", viewName);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        String viewName = productController.createProduct(product, model);
        verify(productService).create(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testListProduct() {
        List<Product> mockProducts = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product A");
        product1.setProductQuantity(5);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product B");
        product2.setProductQuantity(10);

        mockProducts.add(product1);
        mockProducts.add(product2);

        when(productService.findAll()).thenReturn(mockProducts);

        String viewName = productController.listProduct(model);
        verify(model).addAttribute("products", mockProducts);
        assertEquals("productList", viewName);
    }

    @Test
    void testUpdateProductPage() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Product A");
        product.setProductQuantity(5);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.updateProductPage("1", model);
        verify(model).addAttribute("product", product);
        assertEquals("updateProduct", viewName);
    }

    @Test
    void testUpdateProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("1");
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(15);

        String viewName = productController.updateProduct("1", updatedProduct, model);
        verify(productService).updateProduct("1", "Updated Product", 15);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testDeleteProduct() {
        String viewName = productController.deleteProduct("1");
        verify(productService).deleteProduct("1");
        assertEquals("redirect:/product/list", viewName);
    }
}
