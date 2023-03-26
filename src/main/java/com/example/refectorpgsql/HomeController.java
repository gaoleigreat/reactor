package com.example.refectorpgsql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/books")
@Slf4j
public class HomeController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("")
    public Flux<Book> getHome() {

        return bookRepository.findAll();

    }

    @GetMapping("test")
    public Flux<Book> postBook() {

        List<Book> bookList = new ArrayList<>();
        log.info("start");
        for (long i = 1; i < 200000L; i++) {
            Book temp = new Book(i, i + "book", i + "author");
            bookList.add(temp);
        }

        Flux<Book> bookFlux = bookRepository.saveAll(bookList).doOnComplete(() -> log.info("end"));

        return bookFlux;
    }

    @PutMapping("")
    public Mono<Book> updateBook(@RequestBody Book book) {

        return bookRepository.save(book);

    }

    @DeleteMapping("")
    public boolean deleteBook(@RequestBody Book book) {

        try {
            bookRepository.deleteById(book.getId()).block(); // Note this!
            return true;

        } catch (Exception e) {

            return false;
        }
    }
}