package com.devtiro.book.publisher.controllers;

import com.devtiro.book.publisher.domain.Book;
import com.devtiro.book.publisher.repositories.BookRepository;
import com.devtiro.book.publisher.services.BookPublisherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/publisher")
public class PublisherController {

    private final BookPublisherService bookPublisherService;
    private final BookRepository bookRepository;

    public PublisherController(BookPublisherService bookPublisherService, BookRepository bookRepository) {
        this.bookPublisherService = bookPublisherService;
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public ResponseEntity<Book> publishbook(@RequestBody Book book){
        bookPublisherService.publish(book);
        return ResponseEntity.ok(bookRepository.save(book));
    }

    @GetMapping
    public Page<Book> getAllBooks(final Pageable pageable){
        return bookPublisherService.listBooks(pageable);
    }
}
