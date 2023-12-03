package server.mapper.artist_info.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ArtistInfoResponses(
        @JsonProperty("artistInfoResponses")
        List<ArtistInfoResponse> artistInfoResponses
) {
}
