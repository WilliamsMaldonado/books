package com.appgate.book.app.controllers;

import com.appgate.book.app.entity.Book;
import com.appgate.book.app.exceptions.UploadFileException;
import com.appgate.book.app.services.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
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

    @Test
    public void delete() {
        when(service.deleteBook(Mockito.any())).thenReturn(new Book());
        ResponseEntity<Book> response = controller.deleteBook(new Book());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteNotFound() {
        when(service.deleteBook(Mockito.any())).thenReturn(null);
        ResponseEntity<Book> response = controller.deleteBook(new Book());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void upload() throws IOException {
        when(service.store(Mockito.any())).thenReturn(new Iterable<Book>() {
            @Override
            public Iterator<Book> iterator() {
                return new ArrayList<Book>().iterator();
            }
        });
        MultipartFile file = new MockMultipartFile("name.json",
                "file content".getBytes(StandardCharsets.UTF_8));
        ResponseEntity<String> response = controller.uploadFile(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void uploadException() throws IOException {
        when(service.store(Mockito.any())).thenThrow(new UploadFileException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR"));
        MultipartFile file = new MockMultipartFile("name.json",
                "file content".getBytes(StandardCharsets.UTF_8));
        ResponseEntity<String> response = controller.uploadFile(file);
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
    }

    @Test
    public void getByAuthor() {
        when(service.getBookByAuthor(Mockito.anyString())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Book>> response = controller.getByAuthor("");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
