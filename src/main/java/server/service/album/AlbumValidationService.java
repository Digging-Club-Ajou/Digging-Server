package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repository.album.AlbumRepository;

@Service
public class AlbumValidationService {

    private final AlbumRepository albumRepository;

    public AlbumValidationService(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Transactional(readOnly = true)
    public boolean validateExistByMemberId(final long memberId) {
        return albumRepository.existsByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public boolean validateExistByAlbumId(final long albumId) {
        return albumRepository.existsByAlbumId(albumId);
    }
}
