
package com.example.a3;

import com.example.a3.enitity.Cart;
import com.example.a3.enitity.Product;
import com.example.a3.enitity.Stock;
import com.example.a3.enitity.Store;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Single-file JUnit 5 test suite running under @SpringBootTest.
 * Focuses on Bean Validation constraints across Store, Product, Stock,
 * and logical validation in Cart constructor.
 */
@SpringBootTest
class A3ApplicationTests {

    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------
    private static void assertHasViolationOnProperty(Object bean, String property) {
        Set<ConstraintViolation<Object>> violations = validator.validate(bean);
        boolean match = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals(property));
        if (!match) {
            String details = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .reduce("", (a, b) -> a + (a.isEmpty() ? "" : "; ") + b);
            fail("Expected a violation on property '" + property + "' but got: " + details);
        }
    }

    private static void assertNoViolations(Object bean) {
        Set<ConstraintViolation<Object>> violations = validator.validate(bean);
        assertTrue(violations.isEmpty(), "Expected no violations, but got: " + violations);
    }

    // =======================================================================
    // STORE VALIDATIONS
    // =======================================================================

    @Test
    void store_nameBlank_shouldFailValidation() {
        Store s = new Store();
        s.setName("   "); // @NotBlank
        s.setAddress("Some Address");
        s.setProducts(List.of());
        assertHasViolationOnProperty(s, "name");
    }

    @Test
    void store_nameTooLong_shouldFailValidation() {
        Store s = new Store();
        s.setName("A".repeat(201)); // @Length(max=200)
        s.setAddress("Any");
        s.setProducts(List.of());
        assertHasViolationOnProperty(s, "name");
    }

    // =======================================================================
    // PRODUCT VALIDATIONS
    // =======================================================================

    @Test
    void product_skuBlank_shouldFailValidation() {
        Product p = new Product();
        p.setStore(new Store("Valid Store", "Addr"));
        p.setSku("   "); // @NotBlank
        p.setName("Valid Name");
        p.setPrice(BigDecimal.ZERO);
        p.setDescription("ok");
        p.setStocks(List.of());
        assertHasViolationOnProperty(p, "sku");
    }

    @Test
    void product_nameBlank_shouldFailValidation() {
        Product p = new Product();
        p.setStore(new Store("Valid Store", "Addr"));
        p.setSku("SKU-001");
        p.setName("   "); // @NotBlank
        p.setPrice(BigDecimal.ONE);
        p.setDescription("ok");
        p.setStocks(List.of());
        assertHasViolationOnProperty(p, "name");
    }

    @Test
    void product_priceNegative_shouldFailValidation() {
        Product p = new Product();
        p.setStore(new Store("Valid Store", "Addr"));
        p.setSku("SKU-002");
        p.setName("Valid Name");
        p.setPrice(new BigDecimal("-1.00")); // @PositiveOrZero
        p.setDescription("ok");
        p.setStocks(List.of());
        assertHasViolationOnProperty(p, "price");
    }

    // =======================================================================
    // STOCK VALIDATIONS + BUSINESS RULES
    // =======================================================================

    @Test
    void stock_quantityNegative_shouldFailValidation() {
        Stock st = new Stock();
        st.setStore(new Store("Store", "Addr"));
        st.setProduct(new Product(new Store("Store", "Addr"), "SKU-X", "Prod", "desc", BigDecimal.TEN));
        st.setQuantity(-5); // @Min(0)
        assertHasViolationOnProperty(st, "quantity");
    }

    @Test
    void stock_storeOrProductNull_shouldFailValidation() {
        Stock st1 = new Stock();
        st1.setStore(null); // @NotNull
        st1.setProduct(new Product(new Store("Store", "Addr"), "SKU-Y", "Prod", "desc", BigDecimal.ONE));
        st1.setQuantity(0);
        assertHasViolationOnProperty(st1, "store");

        Stock st2 = new Stock();
        st2.setStore(new Store("Store", "Addr"));
        st2.setProduct(null); // @NotNull
        st2.setQuantity(0);
        assertHasViolationOnProperty(st2, "product");
    }

    // =======================================================================
    // CART CONSTRUCTOR VALIDATION (logic-level, not bean)
    // =======================================================================

    @Test
    void cart_invalidStatusInConstructor_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cart("INVALID", OffsetDateTime.now(), new HashSet<>()));
    }
    

 // Store constructor validation
 @Test
 void store_constructor_blankName_shouldThrow() {
     assertThrows(IllegalArgumentException.class, () ->
             new Store("   ", "Addr"));
 }

 @Test
 void store_constructor_nameTooLong_shouldThrow() {
     assertThrows(IllegalArgumentException.class, () ->
             new Store("A".repeat(201), "Addr"));
 }

 @Test
 void store_constructor_addressTooLong_shouldThrow() {
     assertThrows(IllegalArgumentException.class, () ->
             new Store("Valid", "A".repeat(301)));
 }

}
