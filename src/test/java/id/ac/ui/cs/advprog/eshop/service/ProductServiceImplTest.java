package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(any(Product.class))).thenReturn(product);
        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getProductName());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());
        List<Product> products = productService.findAll();

        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateExistingProduct() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        doNothing().when(productRepository).updateProduct("1", "Updated Product", 20);

        Product updatedProduct = productService.updateProduct("1", "Updated Product", 20);
        assertNotNull(updatedProduct);
        assertEquals("Test Product", updatedProduct.getProductName()); // Original product is mocked
        verify(productRepository, times(1)).updateProduct("1", "Updated Product", 20);
    }

    @Test
    void testUpdateNonExistingProduct() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.updateProduct("999", "Updated Product", 20));
        verify(productRepository, times(1)).updateProduct("999", "Updated Product", 20);
    }

    @Test
    void testDeleteExistingProduct() {
        when(productRepository.delete("1")).thenReturn(true);
        assertDoesNotThrow(() -> productService.deleteProduct("1"));
        verify(productRepository, times(1)).delete("1");
    }

    @Test
    void testDeleteNonExistingProduct() {
        when(productRepository.delete("999")).thenReturn(false);
        assertThrows(RuntimeException.class, () -> productService.deleteProduct("999"));
        verify(productRepository, times(1)).delete("999");
    }
}
