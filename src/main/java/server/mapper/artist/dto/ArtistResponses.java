package server.mapper.artist.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ArtistResponses (
        List<ArtistResponse> artistResponses
){


}
