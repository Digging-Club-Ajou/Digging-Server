package server.mapper.genre.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.joda.time.DateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GenreRequest(
        Long id,
        Long memberId,
        int kPop,
        int jPop,
        int rock,
        int pop,
        String createAt,
        String lastModifiedAt
){

}
