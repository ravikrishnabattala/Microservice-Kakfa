package com.devtiro.book.publisher.services.impl;

import com.devtiro.book.publisher.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.devtiro.book.publisher.config.KafkaConfigProps;
import com.devtiro.book.publisher.domain.Book;
import com.devtiro.book.publisher.exceptions.BookPublishException;
import com.devtiro.book.publisher.services.BookPublisherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.domain.Pageable;

/**
 * Publishes books to a Kafka topic.
 */
@Service
public class KafkaBookPublisherService implements BookPublisherService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;

    private final BookRepository bookRepository;

    public KafkaBookPublisherService(
            final ObjectMapper objectMapper,
            final KafkaTemplate<String, String> kafkaTemplate,
            final KafkaConfigProps kafkaConfigProps, BookRepository bookRepository) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfigProps = kafkaConfigProps;
        this.bookRepository = bookRepository;
    }

    @Override
    public Book publish(final Book book) {
        try {
            final String payload = objectMapper.writeValueAsString(book);
            kafkaTemplate.send(kafkaConfigProps.getTopic(), payload);
            return bookRepository.save(book);
        } catch (final JsonProcessingException ex) {
            throw new BookPublishException("Unable to publish book", ex, book);
        }
    }

    @Override
    public Page<Book> listBooks(final Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

}
