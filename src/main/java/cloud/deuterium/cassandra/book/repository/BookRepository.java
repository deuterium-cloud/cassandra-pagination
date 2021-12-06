package cloud.deuterium.cassandra.book.repository;

import cloud.deuterium.cassandra.book.entity.Book;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Milan Stojkovic 06-Dec-2021
 */

@Repository
public interface BookRepository extends CassandraRepository<Book, String> {
}
