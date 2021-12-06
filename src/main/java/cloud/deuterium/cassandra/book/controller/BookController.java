package cloud.deuterium.cassandra.book.controller;

import cloud.deuterium.cassandra.book.dto.BookRequest;
import cloud.deuterium.cassandra.book.dto.CassandraPage;
import cloud.deuterium.cassandra.book.entity.Book;
import cloud.deuterium.cassandra.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by Milan Stojkovic 06-Dec-2021
 */

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<CassandraPage<Book>> getAllBooks(@RequestParam @Min(1) @Max(10) int limit,
                                                           @RequestParam(value = "pagingState", required = false) String pagingState){
        CassandraPage<Book> page = bookService.getAll(limit, pagingState);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn){
        Book book = bookService.getByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> saveNewBook(@RequestBody BookRequest request){
        Book book = bookService.saveNew(request);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBookByIsbn(@PathVariable String isbn){
        bookService.getByIsbn(isbn);
        return ResponseEntity.noContent().build();
    }

}
