package com.appgate.book.app.services;

import com.appgate.book.app.entity.Book;
import com.appgate.book.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().forEach(bookList::add);
        return bookList;
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        List<Book> bookList = bookRepository.findByTitle(title);
        return bookList;
    }

    @Override
    public Book saveBook(Book book) {
        if(book.getId() == null) {
            return bookRepository.save(book);
        }
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        if(book.getId() != null && bookRepository.existsById(book.getId())) {
            return bookRepository.save(book);
        }
        return null;
    }
}
