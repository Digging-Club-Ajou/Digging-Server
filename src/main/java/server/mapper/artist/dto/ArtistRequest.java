package server.mapper.artist.dto;

import java.util.List;

public record ArtistRequest(
        List<String> artistNames
) {
}
