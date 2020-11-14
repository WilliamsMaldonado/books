package com.appgate.book.app.controllers;

import com.appgate.book.app.entity.Book;
import com.appgate.book.app.services.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

	private static final Logger LOGGER = LogManager.getLogger(BooksController.class);

	@Autowired
	private BookService service;

	@GetMapping({"/list", "/", ""})
	public ResponseEntity<List<Book>> getAll() {
		LOGGER.info("GET ALL");
		List<Book> bookList = service.getAllBooks();
		return new ResponseEntity<>(bookList, HttpStatus.OK);
	}

	@GetMapping("/byid/{id}")
	public ResponseEntity<Book> getById(@PathVariable Long id) {
		LOGGER.info("GET BY ID: {}", id);
		Book book = service.getBookById(id);
		return new ResponseEntity<>(book, book == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@GetMapping("/bytitle/{title}")
	public ResponseEntity<List<Book>> getByTitle(@PathVariable String title) {
		LOGGER.info("GET BY TITLE: {}", title);
		List<Book> bookList = service.getBookByTitle(title);
		return new ResponseEntity<>(bookList, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		LOGGER.info("SAVE BOOK: {}", book.getTitle());
		Book bookSave = service.saveBook(book);
		return new ResponseEntity<>(bookSave, bookSave == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Book> updateBook(@RequestBody Book book) {
		LOGGER.info("UPDATE BOOK: {}", book.getTitle());
		Book bookSave = service.updateBook(book);
		return new ResponseEntity<>(bookSave, bookSave == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
}
