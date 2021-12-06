package cloud.deuterium.cassandra.book.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Created by Milan Stojkovic 06-Dec-2021
 */

@Table(value = Book.TABLE_NAME)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    public static final String TABLE_NAME                   = "books";
    public static final String ISBN                         = "book_isbn";
    public static final String TITLE                        = "book_title";
    public static final String PUBLISHER                    = "publisher";
    public static final String AUTHOR                      = "author";

    @PrimaryKeyColumn(name = ISBN, ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String isbn;
    @Column(TITLE)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String title;
    @Column(PUBLISHER)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String publisher;
    @Column(AUTHOR)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String author;
}
