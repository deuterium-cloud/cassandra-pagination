package cloud.deuterium.cassandra.book.dto;

/**
 * Created by Milan Stojkovic 06-Dec-2021
 */
public record BookRequest(
        String isbn,
        String title,
        String publisher,
        String author
) {
}
