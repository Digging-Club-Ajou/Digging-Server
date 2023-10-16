package server.service.spotify;

import server.mapper.spotify.SpotifySearchDto;

import java.util.List;

public interface SpotifySearchArtistService {

    List<SpotifySearchDto> searchArtist(final String search);

}
