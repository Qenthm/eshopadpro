package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    // ... Keep your existing tests for create and findAll ...

    @Nested
    class EditTests {
        @Test
        void testUpdateExistingProduct() {
            Product product = new Product();
            product.setProductName("Sampo Cap Bambang");
            product.setProductQuantity(100);
            Product createdProduct = productRepository.create(product);

            productRepository.updateProduct(createdProduct.getProductId(), "Sampo Cap Usep", 50);

            Optional<Product> updatedProductOptional = productRepository.findById(createdProduct.getProductId());
            assertTrue(updatedProductOptional.isPresent());
            Product updatedProduct = updatedProductOptional.get();
            assertEquals("Sampo Cap Usep", updatedProduct.getProductName());
            assertEquals(50, updatedProduct.getProductQuantity());
        }

        @Test
        void testUpdateWithNegativeQuantity() {
            Product product = new Product();
            product.setProductName("Sampo Cap Bambang");
            product.setProductQuantity(100);
            Product createdProduct = productRepository.create(product);

            assertThrows(IllegalArgumentException.class, () -> {
                productRepository.updateProduct(createdProduct.getProductId(), "Sampo Cap Usep", -1);
            });
        }

        @Test
        void testUpdateNonExistingProduct() {
            assertThrows(RuntimeException.class, () -> {
                productRepository.updateProduct("non-existing-id", "New Name", 100);
            });
        }
    }

    @Nested
    class DeleteTests {
        @Test
        void testDeleteExistingProduct() {
            Product product = new Product();
            product.setProductName("Sampo Cap Bambang");
            product.setProductQuantity(100);
            Product createdProduct = productRepository.create(product);

            boolean deleteResult = productRepository.delete(createdProduct.getProductId());
            assertTrue(deleteResult);

            Optional<Product> deletedProduct = productRepository.findById(createdProduct.getProductId());
            assertTrue(deletedProduct.isEmpty());
        }

        @Test
        void testDeleteNonExistingProduct() {
            boolean deleteResult = productRepository.delete("non-existing-id");
            assertFalse(deleteResult);
        }

        @Test
        void testDeleteAll() {
            Product product1 = new Product();
            product1.setProductName("Product 1");
            product1.setProductQuantity(100);
            productRepository.create(product1);

            Product product2 = new Product();
            product2.setProductName("Product 2");
            product2.setProductQuantity(50);
            productRepository.create(product2);

            productRepository.deleteAll();
            Iterator<Product> iterator = productRepository.findAll();
            assertFalse(iterator.hasNext());
        }
    }
}