# Tutorial 1
## Reflection 1
After doing the first Excercise, i would say that I have written clean code if there were to be any code that is viewed as not clean, please let me know and I will try my best to learn from it and do a better job next time.


## Reflection 2

### Unit Testing Experience and Best Practices

Writing unit tests has taught me several important lessons about software testing:

#### Quantity vs Quality of Tests
The number of unit tests in a class shouldn't be determined by a fixed number, but rather by:
- Test coverage of critical functionality
- Different scenarios and edge cases
- Business requirements
- Code complexity

#### Ensuring Adequate Test Coverage

To ensure sufficient test coverage, we should:
1. Test happy paths and edge cases
2. Use code coverage tools as guidance
3. Follow the AAA pattern (Arrange, Act, Assert)
4. Test both valid and invalid inputs
5. Focus on testing business-critical paths

#### Code Coverage Insights

Having 100% code coverage doesn't guarantee bug-free code because:
- It only shows which code was executed, not if it works correctly
- Doesn't account for different input combinations
- Doesn't test integration between components
- Missing edge cases are still possible
- Doesn't verify business logic correctness

### Clean Code in Functional Testing

#### Issues with Current Approach

Creating similar test classes with duplicate setup procedures raises several clean code concerns:

1. **Code Duplication**
    - Same setup procedures repeated across test classes
    - Duplicate instance variables
    - Similar configuration code

2. **Maintainability Problems**
    - Changes need to be made in multiple places
    - Higher risk of inconsistencies
    - Increased maintenance effort

#### Suggested Improvements

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

# Tutorial 2
## Reflection

### Code Quality Issues Fixed and Strategy
During the exercise, I identified and fixed several code quality issues:

1. **Unnecessary Exception Handling in `deleteProduct` (ProductController.java)**
   - **Issue:** A try-catch block was catching exceptions and rethrowing them unnecessarily, leading to redundant exception handling.
   - **Fix:** Removed the try-catch block and allowed exceptions to propagate naturally.

2. **Use of `.orElseThrow()` in Stream Filtering (ProductController.java)**
   - **Issue:** Using `.orElseThrow()` forced an exception instead of handling missing products more gracefully.
   - **Fix:** Replaced `.orElseThrow()` with `.orElse(null)`, avoiding unnecessary exception branches.

3. **Mockito Unnecessary Stubbing Exception in Tests**
   - **Issue:** Some test cases were stubbing methods that were not actually called, leading to an `UnnecessaryStubbingException`.
   - **Fix:** Removed unnecessary stubbing and ensured only required method calls were stubbed.

4. **Redundant Test Cases**
   - **Issue:** Some test cases had unnecessary assertions or repeated code, making them harder to maintain.
   - **Fix:** Refactored tests using reusable methods and improved assertions.

### CI/CD Reflection

Our current CI/CD pipeline follows the **Continuous Integration (CI) and Continuous Deployment (CD)** principles. Below are the reasons why:

1. **Automated Testing and Quality Checks**
   - Every push to the repository triggers **unit tests**, **code quality analysis**, and **build checks**.
   - This ensures that new changes do not break the application before they are merged.

2. **Automated Deployment to PaaS**
   - Using **GitHub Actions**, the pipeline deploys the latest code changes to **Koyeb** automatically.
   - This removes manual deployment steps and reduces human error.

3. **Fast Feedback Loop**
   - Developers get immediate feedback on code quality and deployment status.
   - If a test fails or deployment encounters an issue, the pipeline prevents bad code from going live.

Overall, the CI/CD setup ensures **better code quality, faster development cycles, and reliable deployment**, aligning with the core principles of DevOps.

## Coverage
![image](https://cdn.discordapp.com/attachments/1339939531557965925/1342464394252128377/Screenshot_2025-02-21_at_18.54.06.png?ex=67b9bae5&is=67b86965&hm=3cce92c3fbdba5a353d7b0b21eb77ecd1588d693db92839b7e964cc6b67abecb&)

# Tutorial 3 - Reflection
## SOLID Principles Implementation and Analysis

### 1. Applied SOLID Principles

#### Single Responsibility Principle (SRP)
- Separated car-related operations into dedicated classes:
   - `CarController`: Handles HTTP requests
   - `CarService`: Manages business logic
   - `CarRepository`: Handles data persistence
- Each class has one specific responsibility and reason to change

#### Open/Closed Principle (OCP)
- Created `CarService` interface to allow extending functionality
- New car operations can be added by implementing new methods without modifying existing code
- **Example:** Adding new car validations without changing core service logic

#### Liskov Substitution Principle (LSP)
- `CarServiceImpl` properly implements `CarService` interface
- Any method expecting `CarService` can work with `CarServiceImpl`
- Ensures type safety and behavioral consistency

#### Interface Segregation Principle (ISP)
- `CarService` interface contains only car-related methods
- Clients are not forced to depend on methods they don't use
- Kept interfaces focused and cohesive

#### Dependency Inversion Principle (DIP)
- `CarController` depends on `CarService` interface, not implementation
- Dependencies are injected through constructor
- Enables easier testing and maintenance

---

### 2. Advantages of SOLID Application

#### Improved Maintainability
```java
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }
}
```
### Advantages of SOLID Application

#### Improved Maintainability
- Dependencies are clear and explicit
- Easy to modify single components
- Reduced coupling between classes

#### Enhanced Testability
- Services can be mocked through interfaces
- Components can be tested in isolation
- Reduced test complexity

#### Better Scalability
- New features can be added without changing existing code
- Easy to extend functionality through interfaces
- Clear separation of concerns

---

### 3. Disadvantages of Not Applying SOLID

#### Code Rigidity
Without SOLID:
```java
public class CarManager {
    // Handles HTTP, business logic, and data access
    public void createCar(Car car) { /* ... */ }
    public void updateInventory() { /* ... */ }
    public void generateReport() { /* ... */ }
}
```
- Hard to modify without affecting other parts
- Difficult to maintain and understand
- High risk of introducing bugs

#### Testing Difficulties
- Tightly coupled code is hard to test
- Need to mock multiple dependencies
- Complex setup for unit tests

#### Limited Extensibility
- New features require modifying existing code
- Higher risk of regression bugs
- Difficult to adapt to changing requirements

---

### Conclusion
The application of SOLID principles has significantly improved our codebase's structure, making it more maintainable, testable, and extensible. While the initial setup required more planning and code organization, the long-term benefits far outweigh the initial investment.