package com.appgate.book.app.services;

import com.appgate.book.app.entity.Book;
import com.appgate.book.app.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
}
