package server.service.spotify;

import server.mapper.spotify.SpotifySearchDto;

import java.util.List;

public interface SpotifySearchArtistService {

    String searchArtist(final String search);

}
