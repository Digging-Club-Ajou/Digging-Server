package server.service.spotify;


import org.springframework.stereotype.Service;
import server.mapper.spotify.SpotifySearchDto;
import server.mapper.spotify.SpotifyArtistDto;

import java.util.List;

@Service
public interface SpotifySearchMusicService {

    List<SpotifySearchDto> searchTracks(final String search);

    List<String> findGenre(final String search);
}
