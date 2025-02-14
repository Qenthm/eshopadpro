package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Nested
    class GetterTests {
        @Test
        void testGetProductId() {
            assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", product.getProductId());
        }

        @Test
        void testGetProductName() {
            assertEquals("Sampo Cap Bambang", product.getProductName());
        }

        @Test
        void testGetProductQuantity() {
            assertEquals(100, product.getProductQuantity());
        }
    }

    @Nested
    class EditTests {
        @Test
        void testEditProductName() {
            product.setProductName("Sampo Cap Usep");
            assertEquals("Sampo Cap Usep", product.getProductName());
        }

        @Test
        void testEditProductQuantity() {
            product.setProductQuantity(50);
            assertEquals(50, product.getProductQuantity());
        }

        @Test
        void testEditProductNameWithEmptyString() {
            assertThrows(IllegalArgumentException.class, () -> {
                product.setProductName("");
            });
        }

        @Test
        void testEditProductNameWithNull() {
            assertThrows(IllegalArgumentException.class, () -> {
                product.setProductName(null);
            });
        }

        @Test
        void testEditProductQuantityWithNegativeValue() {
            assertThrows(IllegalArgumentException.class, () -> {
                product.setProductQuantity(-1);
            });
        }

        @Test
        void testEditProductQuantityWithZero() {
            product.setProductQuantity(0);
            assertEquals(0, product.getProductQuantity());
        }
    }
}