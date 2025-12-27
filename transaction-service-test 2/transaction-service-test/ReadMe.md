SPRING BOOT REST API – TRANSACTION SERVICE (TDD APPROACH)
Context

Design and implement a Spring Boot REST API to manage financial transactions. 
Each transaction records an operation such as credit, debit, refund, transfer, etc. 
The solution must demonstrate clean architecture, DTO usage, strong validation, 
robust exception handling, and must be implemented using Test Driven Development.

Domain Requirements

Entity: Transaction
Fields (exactly these):
Long id
String type
Examples: CREDIT, DEBIT, REFUND
String title
BigDecimal amount
LocalDate date
String description

Persist using in-memory storage only (Java Collections). Do not use any database.

DTO Requirements
Design separate DTOs:

TransactionRequestDTO
TransactionResponseDTO
ErrorResponseDTO

Do not expose entity directly in controllers.
Validation Rules

Apply Bean Validation on Request DTO:

type
 mandatory
 allowed values: CREDIT, DEBIT, REFUND
title
 mandatory
 length 3–50 characters

amount
 mandatory
 must be positive
 allow 2 decimal precision; handle with BigDecimal without precision loss

date
 mandatory
 cannot be future date

description
 optional
 max 200 characters

If validation fails → throw 400 BAD REQUEST with structured ErrorResponseDTO.

===============================================================================================
REST API Endpoints
1. Create Transaction

POST /api/transactions
Accepts TransactionRequestDTO
Returns 201 Created + TransactionResponseDTO

2. Get Transaction by ID

GET /api/transactions/{id}
Return 200 OK + TransactionResponseDTO
If not found → 404 NOT FOUND

3. Get All Transactions

GET /api/transactions
Return list
If no records → return empty list (NOT 404)

4. Delete Transaction

DELETE /api/transactions/{id}
Return 204 No Content
If not found → 404 NOT FOUND

5. Filter Transactions
Endpoint

GET /api/transactions/filter

Query Parameters

All parameters are optional, apply AND logic:

type → CREDIT / DEBIT / REFUND

startDate → inclusive

endDate → inclusive

minAmount

maxAmount

titleContains

descriptionContains

Behavior

If no filters supplied → return all transactions

If result empty → return empty list (NOT error)

Validate dates:

if startDate > endDate → return 400 Bad Request

Validate amount ranges:

if minAmount > maxAmount → return 400 Bad Request

Response

200 OK
Returns List of TransactionResponseDTO

6. Get Balance Summary
Endpoint

GET /api/transactions/balance

Business Rule

Compute:

totalCredit

totalDebit

netBalance = totalCredit - totalDebit

Use correct BigDecimal scale management.

Response DTO Structure:
{
  "totalCredit": 20000.00,
  "totalDebit": 5000.00,
  "netBalance": 15000.00
}

Status

200 OK

7. Get Transactions by Date
Endpoint

GET /api/transactions/date/{date}

Behavior

Returns all transactions on given date

If no transactions found → empty list

Validation

date must be valid ISO date

if invalid → 400 Bad Request

8. Update Transaction
Endpoint

PUT /api/transactions/{id}

Rules

Accept TransactionRequestDTO

If not found → 404 NOT FOUND

Apply same validations as create

Return updated TransactionResponseDTO

Status

200 OK
======================================================================================
Error Handling Requirements

Implement centralized exception handling using:
Handle:
 Validation errors → 400
 Resource not found → 404
 Generic errors → 500

ErrorResponseDTO must include:
 timestamp
 message
 details (optional field)
 statusCode

Mapping Requirement
Implement at least one explicit Mapper layer, not ModelMapper.
Show clean conversion:
 Entity → ResponseDTO
 RequestDTO → Entity

Business Logic Rules

amount must store using BigDecimal with proper scale and rounding

Ensure immutability where logical

Maintain clean service layer abstraction

Repository will use Java Collection; must support:
 save
 findById
 findAll
 deleteById

Sample Request
POST /api/transactions
{
  "type": "CREDIT",
  "title": "Salary",
  "amount": 15000.50,
  "date": "2025-01-10",
  "description": "Monthly salary credit"
}

Sample Error Response
{
  "timestamp": "2025-01-10T10:30:11",
  "statusCode": 400,
  "message": "Validation failed",
  "details": [
      "amount must be positive",
      "title length must be between 3 and 50"
  ]
}
