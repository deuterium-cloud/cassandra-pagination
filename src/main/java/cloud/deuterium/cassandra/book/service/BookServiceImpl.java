package cloud.deuterium.cassandra.book.service;

import cloud.deuterium.cassandra.book.dto.BookRequest;
import cloud.deuterium.cassandra.book.dto.CassandraPage;
import cloud.deuterium.cassandra.book.entity.Book;
import cloud.deuterium.cassandra.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Optional;

/**
 * Created by Milan Stojkovic 06-Dec-2021
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Override
    public Book getByIsbn(String isbn) {
        log.info("Fetching Book with isbn={} from database", isbn);
        Optional<Book> optional = bookRepository.findById(isbn);
        return optional
                .orElse(new Book());
    }

    @Override
    public CassandraPage<Book> getAll(int limit, String pagingState) {
        log.info("Fetching all Books from database with limit={} and pagingState={}", limit, pagingState);
        PageRequest pageRequest = PageRequest.of(0, limit);
        if (pagingState == null) {
            Slice<Book> all = bookRepository.findAll(pageRequest);
            return new CassandraPage<>(all);
        }
        byte[] decodedBytes = Base64.getDecoder().decode(pagingState);
        ByteBuffer buffer = ByteBuffer.wrap(decodedBytes);
        CassandraPageRequest cassandraPageRequest = CassandraPageRequest.of(pageRequest, buffer);
        Slice<Book> all = bookRepository.findAll(cassandraPageRequest);
        return new CassandraPage<>(all);
    }

    @Override
    public Book saveNew(BookRequest request) {
        log.info("Saving new Book: {}", request);
        Book book = map(request);
        return bookRepository.save(book);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        log.info("Deleting Book with isbn={}", isbn);
        bookRepository.deleteById(isbn);
        log.info("Book with isbn={} was successfully deleted", isbn);
    }

    private Book map(BookRequest request){
        return Book.builder()
                .isbn(request.isbn())
                .title(request.title())
                .publisher(request.publisher())
                .author(request.author())
                .build();
    }
}
