
# Expense Practice Project (Spring Boot 3)

**Goal:** Implement a full CRUD API (same as _expense v6_) with proper validation and HTTP status handling. This is a **practice** repo — code stubs include TODOs, and tests are **disabled**. Enable tests one by one as you complete each requirement.

---

## Tech Stack
- Java 17
- Spring Boot 3.3.x (Web, Validation, Data JPA)
- H2 (in-memory)
- JUnit 5, Mockito, Spring MVC Test

---

## Requirements
1. **Entity: `ExpenseEntity`**
   - Add Jakarta validation annotations:
     - `title`: `@NotBlank`, `@Size(max=100)`
     - `description`: `@Size(max=255)`
     - `amount`: `@NotNull`, `@Positive`
     - `currency`: `@NotBlank`, `@Size(min=3, max=3)`
     - `timestamp`: `@NotNull`

2. **Controller: `ExpenseController`** — implement mappings:
   - `POST /api/expenses` → **201 Created** with `Location: /api/expenses/{id}`
   - `GET /api/expenses` → **200 OK**
   - `GET /api/expenses/{id}` → **200 OK** when found, **404 Not Found** when missing, **400 Bad Request** when `id <= 0`
   - `PUT /api/expenses/{id}` → **200 OK** when updated, **404 Not Found** when missing, **400 Bad Request** when `id <= 0` (body is validated)
   - `DELETE /api/expenses/{id}` → **202 Accepted** with body `true` when deleted, **404 Not Found** with body `false` when missing, **400 Bad Request** with body `false` when `id <= 0`

3. **Service/Repostory** — already implemented.

4. **Tests (16 total)**
   - **Entity (4):** validation constraints
   - **Controller (12):** CRUD and status codes (enable them gradually)

---

## Changes Needed (Step-by-step)
1. **Add validation annotations** in `ExpenseEntity` (see Requirements #1).
2. **Implement** controller methods in `ExpenseController` (see Requirements #2).
3. **Uncomment/enable tests**: remove `@Disabled` from one test at a time and make it pass before enabling the next:
   - Enable `E1` → make it pass → enable `E2` → etc.
   - Enable `C1` (POST) → pass → enable `C2` → `C3` → ... → `C12`.

---

## Build & Run
```bash
mvn clean test            # runs tests (disabled by default)

# enable some tests by removing @Disabled, then:
mvn -Dtest=*ValidationTest,*ControllerTest test

mvn spring-boot:run       # start the app on port 8080
```

H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:expensedb`, username: `sa`, password: empty)

---

## Acceptance Criteria
- All 16 tests pass after you implement the TODOs.
- API contracts match the status rules above.
- Validation messages are applied (e.g., NotBlank/Positive/Size).

---

## Tips
- Use `@Validated` on the controller class and `@Valid` on request bodies.
- For `Location` header, use:
  ```java
  ResponseEntity.created(URI.create("/api/expenses/" + saved.getId())).body(saved);
  ```
- For `DELETE` boolean body, return:
  ```java
  // 400
  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
  // 404
  ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
  // 202
  ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
  ```

---

## What NOT included
- The final controller logic and validation annotations (you will add them).
- Enabled tests (you will progressively enable them).

Happy practicing! 🚀
