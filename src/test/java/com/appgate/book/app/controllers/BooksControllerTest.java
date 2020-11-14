package com.appgate.book.app.controllers;

import com.appgate.book.app.entity.Book;
import com.appgate.book.app.services.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class BooksControllerTest {

    @Mock private BookService service;

    @InjectMocks private BooksController controller;

    @Test
    public void getAll() {
        when(service.getAllBooks()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Book>> response = controller.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getById() {
        when(service.getBookById(Mockito.anyLong())).thenReturn(new Book());
        ResponseEntity<Book> response = controller.getById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getByIdNotFound() {
        when(service.getBookById(Mockito.anyLong())).thenReturn(null);
        ResponseEntity<Book> response = controller.getById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getByTitle() {
        when(service.getBookByTitle(Mockito.anyString())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Book>> response = controller.getByTitle("");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void save() {
        when(service.saveBook(Mockito.any())).thenReturn(new Book());
        ResponseEntity<Book> response = controller.saveBook(new Book());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void saveBadRequest() {
        when(service.saveBook(Mockito.any())).thenReturn(null);
        ResponseEntity<Book> response = controller.saveBook(new Book());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void update() {
        when(service.updateBook(Mockito.any())).thenReturn(new Book());
        ResponseEntity<Book> response = controller.updateBook(new Book());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void saveNotFound() {
        when(service.updateBook(Mockito.any())).thenReturn(null);
        ResponseEntity<Book> response = controller.updateBook(new Book());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
