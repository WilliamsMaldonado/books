package com.appgate.book.app.repository;

import com.appgate.book.app.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);
}
