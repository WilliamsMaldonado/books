package com.appgate.book.app.services;

import com.appgate.book.app.entity.Book;
import com.appgate.book.app.exceptions.UploadFileException;
import com.appgate.book.app.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    @Mock private BookRepository repository;

    @InjectMocks private BookServiceImpl bookService;

    @Test
    public void getAllBooks() {
        when(repository.findAll()).thenReturn(new Iterable<Book>() {
            @Override
            public Iterator<Book> iterator() {
                return new ArrayList<Book>().iterator();
            }
        });
        List<Book> books = bookService.getAllBooks();
        assertNotNull(books);
    }

    @Test
    public void getBookById() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Book()));
        Book book = bookService.getBookById(1L);
        assertNotNull(book);
    }

    @Test
    public void getBookByTitle() {
        when(repository.findByTitle(Mockito.anyString())).thenReturn(new ArrayList<>());
        List<Book> bookList = bookService.getBookByTitle("title");
        assertNotNull(bookList);
    }

    @Test
    public void saveBook() {
        when(repository.save(Mockito.any())).thenReturn(new Book());
        Book book = bookService.saveBook(new Book());
        assertNotNull(book);
    }

    @Test
    public void saveBookNull() {
        Book book = new Book();
        book.setId(1L);
        Book bookSave = bookService.saveBook(book);
        assertNull(bookSave);
    }

    @Test
    public void updateBook() {
        Book book = new Book();
        book.setId(1L);
        when(repository.save(Mockito.any())).thenReturn(book);
        when(repository.existsById(Mockito.anyLong())).thenReturn(true);
        Book bookSave = bookService.updateBook(book);
        assertNotNull(bookSave);
    }

    @Test
    public void updateBookNull() {
        Book book = bookService.updateBook(new Book());
        assertNull(book);
    }

    @Test
    public void getBookByAuthor() {
        when(repository.findByAuthor(Mockito.anyString())).thenReturn(new ArrayList<>());
        List<Book> bookList = bookService.getBookByAuthor("title");
        assertNotNull(bookList);
    }

    @Test
    public void deleteBook() {
        Book book = new Book();
        book.setId(1L);
        when(repository.existsById(Mockito.anyLong())).thenReturn(true);
        Book bookSave = bookService.deleteBook(book);
        assertNotNull(bookSave);
    }

    @Test
    public void deleteBookNull() {
        Book book = bookService.deleteBook(new Book());
        assertNull(book);
    }

    @Test(expected = UploadFileException.class)
    public void uploadFileError() throws IOException {
        MultipartFile file = new MockMultipartFile("name.txt",
                "file content".getBytes(StandardCharsets.UTF_8));
        bookService.store(file);
    }

    @Test
    public void uploadFile() throws IOException {
        File file = new File("src/main/resources/exampleUploadFile.json");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", Files.readAllBytes(Paths.get(file.toURI())));
        Iterable<Book> books = bookService.store(multipartFile);
        when(repository.saveAll(Mockito.any(Iterable.class))).thenReturn(new Iterable<Book>() {
            @Override
            public Iterator<Book> iterator() {
                return new ArrayList<Book>().iterator();
            }
        });
        assertNull(books);
    }
}
