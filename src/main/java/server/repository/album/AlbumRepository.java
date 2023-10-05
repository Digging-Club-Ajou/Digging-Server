package server.repository.album;

import org.springframework.stereotype.Repository;
import server.domain.profile.Album;

@Repository
public class AlbumRepository {

    private final AlbumJpaRepository albumJpaRepository;

    public AlbumRepository(final AlbumJpaRepository albumJpaRepository) {
        this.albumJpaRepository = albumJpaRepository;
    }

    public void save(final Album album) {
        albumJpaRepository.save(album);
    }

    public boolean existsByMemberId(final long memberId) {
        return albumJpaRepository.existsByMemberId(memberId);
    }
}
