package com.devtiro.book.publisher.services;

import com.devtiro.book.publisher.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Publishes books.
 */
public interface BookPublisherService {
    Book publish(Book book);
    Page<Book> listBooks(Pageable pageable);

}
