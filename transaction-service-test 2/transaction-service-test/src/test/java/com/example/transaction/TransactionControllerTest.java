package com.example.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final String VALID_REQUEST = """
			{
			  "type": "CREDIT",
			  "title": "Salary",
			  "amount": 1500.50,
			  "date": "2024-12-01",
			  "description": "Monthly credit"
			}
			""";

	private String buildRequest(String type) {
		return """
				{
				  "type": "%s",
				  "title": "Test",
				  "amount": 100,
				  "date": "2024-12-01",
				  "description": "test"
				}
				""".formatted(type);
	}

	@Test
	@DisplayName("Invalid type should return 400 Bad Request")
	void invalidType_shouldFail() throws Exception {
		mockMvc.perform(
				post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(buildRequest("TRANSFER")))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Validation Failed"));
	}

	@Test
	@DisplayName("Blank type should return 400 Bad Request")
	void blankType_shouldFail() throws Exception {
		mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(buildRequest("")))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Valid CREDIT should pass")
	void validCredit_shouldPass() throws Exception {
		mockMvc.perform(
				post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(buildRequest("CREDIT")))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Valid DEBIT should pass")
	void validDebit_shouldPass() throws Exception {
		mockMvc.perform(
				post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(buildRequest("DEBIT")))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Valid REFUND should pass")
	void validRefund_shouldPass() throws Exception {
		mockMvc.perform(
				post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(buildRequest("REFUND")))
				.andExpect(status().isCreated());
	}

	@Test
	void create_success() throws Exception {
		mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(VALID_REQUEST))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.type").value("CREDIT"));
	}

	@Test
	void create_validationFail() throws Exception {
		mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content("""
				    {
				      "type": "CREDIT",
				      "title": "",
				      "amount": 10,
				      "date": "2023-01-01"
				    }
				""")).andExpect(status().isBadRequest());

		mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content("""
				    {
				      "type": "DEBIT",
				      "title": "salary",
				      "amount": -10,
				      "date": "2023-01-01"
				    }
				""")).andExpect(status().isBadRequest());
		
		mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content("""
				    {
				      "type": "DEBIT",
				      "title": "salary",
				      "amount": 10,
				      "date": "2030-01-01"
				    }
				""")).andExpect(status().isBadRequest());
	}

	@Test
	void getById_notFound() throws Exception {
		mockMvc.perform(get("/api/transactions/999")).andExpect(status().isNotFound());
	}

	@Test
	void update_notFound() throws Exception {
		mockMvc.perform(put("/api/transactions/300").contentType(MediaType.APPLICATION_JSON).content(VALID_REQUEST))
				.andExpect(status().isNotFound());
	}

	@Test
	void delete_notFound() throws Exception {
		mockMvc.perform(delete("/api/transactions/1000")).andExpect(status().isNotFound());
	}
	@Test
	void getBalance_success() throws Exception {
		mockMvc.perform(get("/api/transactions/balance")).andExpect(status().isOk())
				.andExpect(jsonPath("$.totalCredit").exists()).andExpect(jsonPath("$.totalDebit").exists())
				.andExpect(jsonPath("$.netBalance").exists());
	}
}
