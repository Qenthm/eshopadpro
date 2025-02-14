# Reflection 1
After doing the first Excercise, i would say that I have written clean code if there were to be any code that is viewed as not clean, please let me know and I will try my best to learn from it and do a better job next time.


# Reflection 2

## Unit Testing Experience and Best Practices

Writing unit tests has taught me several important lessons about software testing:

### Quantity vs Quality of Tests
The number of unit tests in a class shouldn't be determined by a fixed number, but rather by:
- Test coverage of critical functionality
- Different scenarios and edge cases
- Business requirements
- Code complexity

### Ensuring Adequate Test Coverage

To ensure sufficient test coverage, we should:
1. Test happy paths and edge cases
2. Use code coverage tools as guidance
3. Follow the AAA pattern (Arrange, Act, Assert)
4. Test both valid and invalid inputs
5. Focus on testing business-critical paths

### Code Coverage Insights

Having 100% code coverage doesn't guarantee bug-free code because:
- It only shows which code was executed, not if it works correctly
- Doesn't account for different input combinations
- Doesn't test integration between components
- Missing edge cases are still possible
- Doesn't verify business logic correctness

## Clean Code in Functional Testing

### Issues with Current Approach

Creating similar test classes with duplicate setup procedures raises several clean code concerns:

1. **Code Duplication**
    - Same setup procedures repeated across test classes
    - Duplicate instance variables
    - Similar configuration code

2. **Maintainability Problems**
    - Changes need to be made in multiple places
    - Higher risk of inconsistencies
    - Increased maintenance effort

### Suggested Improvements

1. **Create a Base Test Class**
   ```java
   public abstract class BaseFunctionalTest {
       protected WebDriver driver;
       protected String baseUrl;
       
       @BeforeEach
       public void setUp() {
           // Common setup code
       }
       
       @AfterEach
       public void tearDown() {
           // Common cleanup code
       }
       
       // Common utility methods
   }
   ```

2. **Use Page Object Pattern**
    - Encapsulate page-specific elements and actions
    - Improve test maintainability
    - Make tests more readable

3. **Implement Test Utilities**
    - Create shared assertion methods
    - Common test data generation
    - Reusable test helpers

4. **Follow DRY Principle**
    - Extract common test scenarios into shared methods
    - Use test data builders
    - Implement shared setup fixtures

These improvements would result in:
- More maintainable tests
- Better code organization
- Reduced duplication
- Easier test updates
- More reliable test execution

The goal is to create a testing framework that is both robust and maintainable while ensuring proper test coverage of the application's functionality.