package com.appgate.book.app.services;

import com.appgate.book.app.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    List<Book> getBookByTitle(String title);

    Book saveBook(Book book);

    Book updateBook(Book book);
}
