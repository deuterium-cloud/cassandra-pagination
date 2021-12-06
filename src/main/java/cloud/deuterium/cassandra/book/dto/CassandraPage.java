package cloud.deuterium.cassandra.book.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.lang.Nullable;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by Milan Stojkovic 06-Dec-2021
 */

@Data
public class CassandraPage<T> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Integer count;
    private final List<T> content;
    private final Boolean hasNext;
    private final String pagingState;

    public CassandraPage(final Slice<T> slice) {
        this.content = slice.getContent();
        this.count = content.size();
        this.hasNext = slice.hasNext();
        this.pagingState = getPagingState(slice);
    }

    @Nullable
    private static <T> String getPagingState(final Slice<T> slice) {
        if (slice.hasNext()) {
            CassandraPageRequest pageRequest = (CassandraPageRequest) slice.nextPageable();
            ByteBuffer pagingState = pageRequest.getPagingState();
            try {
                String asString = MAPPER.writeValueAsString(pagingState);
                return asString.substring(1, asString.length() - 1); // remove "" added by ObjectMapper
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

}
