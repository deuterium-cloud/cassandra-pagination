package cloud.deuterium.cassandra.book.service;

import cloud.deuterium.cassandra.book.dto.BookRequest;
import cloud.deuterium.cassandra.book.dto.CassandraPage;
import cloud.deuterium.cassandra.book.entity.Book;

/**
 * Created by Milan Stojkovic 06-Dec-2021
 */
public interface BookService {
    Book getByIsbn(String isbn);
    CassandraPage<Book> getAll(int limit, String pagingState);
    Book saveNew(BookRequest request);
    void deleteByIsbn(String isbn);
}
