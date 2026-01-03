
package com.example.expense.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Practice specs: remove @Disabled and implement the entity annotations to make tests pass.
 */
class ExpenseEntityValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Disabled("TODO: add validation annotations and enable")
    @Test
    void E1_validExpense_shouldPass() {
        ExpenseEntity e = new ExpenseEntity(
                null,
                "Lunch",
                "Client lunch",
                new BigDecimal("250.50"),
                "INR",
                LocalDateTime.now()
        );
        Set<ConstraintViolation<ExpenseEntity>> violations = validator.validate(e);
        assertTrue(violations.isEmpty(), "Expected no violations");
    }

    @Disabled("TODO: add @NotBlank on title")
    @Test
    void E2_blankTitle_shouldFailNotBlank() {
        ExpenseEntity e = new ExpenseEntity(
                null,
                "  ",
                "Desc",
                new BigDecimal("10.00"),
                "USD",
                LocalDateTime.now()
        );
        Set<ConstraintViolation<ExpenseEntity>> violations = validator.validate(e);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Disabled("TODO: add @Positive on amount")
    @Test
    void E3_negativeAmount_shouldFailPositive() {
        ExpenseEntity e = new ExpenseEntity(
                null,
                "Taxi",
                "Airport",
                new BigDecimal("-1.00"),
                "EUR",
                LocalDateTime.now()
        );
        Set<ConstraintViolation<ExpenseEntity>> violations = validator.validate(e);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("amount")));
    }

    @Disabled("TODO: add @Size(min=3,max=3) on currency")
    @Test
    void E4_wrongCurrencyLength_shouldFailSize() {
        ExpenseEntity e = new ExpenseEntity(
                null,
                "Parking",
                "",
                new BigDecimal("50.00"),
                "IN",
                LocalDateTime.now()
        );
        Set<ConstraintViolation<ExpenseEntity>> violations = validator.validate(e);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("currency")));
    }
}
