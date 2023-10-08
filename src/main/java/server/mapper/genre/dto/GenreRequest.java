package server.mapper.genre.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.joda.time.DateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GenreRequest(
        Long id,
        boolean kPop,
        boolean jPop,
        boolean rock,
        boolean pop

){

}
