package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.profile.Album;
import server.mapper.profile.AlbumMapper;
import server.repository.album.AlbumRepository;

@Service
public class AlbumInfoCreateService {

    private final AlbumRepository albumRepository;

    public AlbumInfoCreateService(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Transactional
    public void createProfileInfo(final long memberId, final String albumName) {
        Album album = AlbumMapper.toEntity(memberId, albumName);
        albumRepository.save(album);
    }
}
