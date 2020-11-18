package com.appgate.book.app.services;

import com.appgate.book.app.entity.Book;
import com.appgate.book.app.exceptions.UploadFileException;
import com.appgate.book.app.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    private static final String EXTENSION = "json";
    private static final String ERROR_EXTENSION_FILE = "ERROR EXTENSION FILE";

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

    @Override
    public Book deleteBook(Book book) {
        if(book.getId() != null && bookRepository.existsById(book.getId())) {
            bookRepository.delete(book);
            return book;
        }
        return null;
    }

    @Override
    public List<Book> getBookByAuthor(String author) {
        List<Book> bookList = bookRepository.findByAuthor(author);
        return bookList;
    }

    public Iterable<Book> store(MultipartFile multipartFile) throws IOException, UploadFileException {
        String extension = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        if (extension.equalsIgnoreCase(EXTENSION)) {
            File file = new File("targetFile.json");
            OutputStream os = new FileOutputStream(file);
            os.write(multipartFile.getBytes());
            List<Book> bookList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            Files.lines(Paths.get(file.toURI())).forEach(line -> {
                try {
                    bookList.add(mapper.readValue(line, Book.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            file.delete();
            return bookRepository.saveAll(bookList);
        }
        throw new UploadFileException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_EXTENSION_FILE);
    }
}
