package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void homePage_isDisplayed(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        assertPageTitle(driver, "ADV Shop");
        assertEquals(true, pageSourceContains(driver, "Welcome"));
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) throws Exception {
        // Start from home page
        driver.get(baseUrl);
        assertPageTitle(driver, "ADV Shop");

        // Navigate to product list
        driver.get(baseUrl + "/product/list");
        assertPageTitle(driver, "Product List");

        // Click create button
        driver.findElement(By.id("create")).click();
        assertPageTitle(driver, "Create New Product");

        // Fill the form
        driver.findElement(By.id("nameInput")).sendKeys("Test Product");
        driver.findElement(By.id("quantityInput")).sendKeys("100");

        // Submit the form
        driver.findElement(By.tagName("form")).submit();

        // Verify we're back on product list page
        assertPageTitle(driver, "Product List");

        // Verify the product appears in the list
        assertEquals(true, pageSourceContains(driver, "Test Product"));
        assertEquals(true, pageSourceContains(driver, "100"));
    }

    @Test
    void createProduct_formValidation(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        assertPageTitle(driver, "Create New Product");

        // Verify form elements exist
        assertFormAction(driver, baseUrl + "/product/create");
        assertFormFieldNotNull(driver, "nameInput");
        assertFormFieldNotNull(driver, "quantityInput");
    }

    // Helper methods
    private void assertPageTitle(ChromeDriver driver, String expectedTitle) {
        String pageTitle = driver.getTitle();
        assertEquals(expectedTitle, pageTitle);
    }

    private void assertFormFieldNotNull(ChromeDriver driver, String fieldId) {
        assertNotNull(driver.findElement(By.id(fieldId)));
    }

    private void assertFormAction(ChromeDriver driver, String expectedAction) {
        String formAction = driver.findElement(By.tagName("form")).getAttribute("action");
        assertEquals(expectedAction, formAction);
    }

    private boolean pageSourceContains(ChromeDriver driver, String expectedContent) {
        String pageSource = driver.getPageSource();
        return pageSource.contains(expectedContent);
    }
}