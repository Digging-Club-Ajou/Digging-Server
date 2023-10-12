package server.service.artist;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.mapper.artist.ArtistMapper;
import server.mapper.artist.dto.ArtistRequest;
import server.repository.artist.ArtistRepository;

import java.util.List;


@Service
public class ArtistCreateService {

    private final ArtistRepository artistRepository;

    public ArtistCreateService(final ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional
    public void createArtists(final long memberId, final ArtistRequest artistRequest) {
        List<String> artistNames = artistRequest.artistNames();
        artistNames.stream()
                .map(
                        artistName -> ArtistMapper.toEntity(memberId, artistName)
                )
                .forEach(artistRepository::save);

        // todo RestTemplate로 ML에 요청
    }
}
