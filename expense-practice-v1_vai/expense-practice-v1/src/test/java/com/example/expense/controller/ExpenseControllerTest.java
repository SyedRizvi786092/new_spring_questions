
package com.example.expense.controller;

import com.example.expense.entity.ExpenseEntity;
import com.example.expense.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    private ExpenseService service;
    private ExpenseController controller;

    @BeforeEach
    void setup() {
        service = Mockito.mock(ExpenseService.class);
        controller = new ExpenseController(service);
    }

    // ----- create -----
    @Test
    void create_Returns201() {
        ExpenseEntity req = new ExpenseEntity();
        ExpenseEntity saved = new ExpenseEntity();
        saved.setId(1L);

        when(service.create(req)).thenReturn(saved);

        ResponseEntity<ExpenseEntity> res = controller.create(req);

        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertEquals(1L, res.getBody().getId());
        verify(service, times(1)).create(req);
    }

    // ----- getAll -----
    @Test
    void getAll_Returns200WithList() {
        ExpenseEntity e1 = new ExpenseEntity(); e1.setId(1L);
        ExpenseEntity e2 = new ExpenseEntity(); e2.setId(2L);
        List<ExpenseEntity> list = Arrays.asList(e1, e2);

        when(service.getAll()).thenReturn(list);

        ResponseEntity<List<ExpenseEntity>> res = controller.getAll();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNotNull(res.getBody());
        assertEquals(2, res.getBody().size());
        verify(service, times(1)).getAll();
    }

    // ----- getById -----
    @Test
    void getById_InvalidId_Returns400() {
        ResponseEntity<ExpenseEntity> res1 = controller.getById(null);
        ResponseEntity<ExpenseEntity> res2 = controller.getById(0L);
        ResponseEntity<ExpenseEntity> res3 = controller.getById(-5L);

        assertEquals(HttpStatus.BAD_REQUEST, res1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, res2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, res3.getStatusCode());

        verifyNoInteractions(service);
    }

    @Test
    void getById_NotFound_Returns404() {
        when(service.getById(100L)).thenReturn(null);

        ResponseEntity<ExpenseEntity> res = controller.getById(100L);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNull(res.getBody());
        verify(service, times(1)).getById(100L);
    }

    @Test
    void getById_Found_Returns200() {
        ExpenseEntity e = new ExpenseEntity(); e.setId(10L);
        when(service.getById(10L)).thenReturn(e);

        ResponseEntity<ExpenseEntity> res = controller.getById(10L);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNotNull(res.getBody());
        assertEquals(10L, res.getBody().getId());
        verify(service, times(1)).getById(10L);
    }

    // ----- update -----
    @Test
    void update_InvalidId_Returns400() {
        ExpenseEntity req = new ExpenseEntity();

        ResponseEntity<ExpenseEntity> res1 = controller.update(null, req);
        ResponseEntity<ExpenseEntity> res2 = controller.update(0L, req);
        ResponseEntity<ExpenseEntity> res3 = controller.update(-1L, req);

        assertEquals(HttpStatus.BAD_REQUEST, res1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, res2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, res3.getStatusCode());

        verifyNoInteractions(service);
    }

    @Test
    void update_NotExists_Returns404() {
        ExpenseEntity req = new ExpenseEntity();
        when(service.exists(5L)).thenReturn(false);

        ResponseEntity<ExpenseEntity> res = controller.update(5L, req);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertNull(res.getBody());
        verify(service, times(1)).exists(5L);
        verify(service, never()).update(anyLong(), any());
    }

    @Test
    void update_Exists_Returns200() {
        ExpenseEntity req = new ExpenseEntity();
        ExpenseEntity updated = new ExpenseEntity(); updated.setId(5L);

        when(service.exists(5L)).thenReturn(true);
        when(service.update(5L, req)).thenReturn(updated);

        ResponseEntity<ExpenseEntity> res = controller.update(5L, req);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNotNull(res.getBody());
        assertEquals(5L, res.getBody().getId());
        verify(service, times(1)).exists(5L);
        verify(service, times(1)).update(5L, req);
    }

    // ----- delete -----
    @Test
    void delete_InvalidId_Returns400() {
        ResponseEntity<Boolean> res1 = controller.delete(null);
        ResponseEntity<Boolean> res2 = controller.delete(0L);
        ResponseEntity<Boolean> res3 = controller.delete(-2L);

        assertEquals(HttpStatus.BAD_REQUEST, res1.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, res2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, res3.getStatusCode());

        verifyNoInteractions(service);
    }

    @Test
    void delete_NotExists_Returns404() {
        when(service.exists(77L)).thenReturn(false);

        ResponseEntity<Boolean> res = controller.delete(77L);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        verify(service, times(1)).exists(77L);
        verify(service, never()).delete(anyLong());
    }

    @Test
    void delete_Exists_Returns204() {
        when(service.exists(88L)).thenReturn(true);

        ResponseEntity<Boolean> res = controller.delete(88L);

        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
        verify(service, times(1)).exists(88L);
        verify(service, times(1)).delete(88L);
    }
}
