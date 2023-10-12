package server.repository.album;

import org.springframework.stereotype.Repository;
import server.domain.album.Album;

import java.util.Optional;

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

    public boolean existsByAlbumName(final String albumName) {
        return albumJpaRepository.existsByAlbumName(albumName);
    }

    public Optional<Album> findByMemberId(final long memberId){return albumJpaRepository.findByMemberId(memberId);}

    public Album getByMemberId(final long memberId){return albumJpaRepository.getByMemberId((memberId));}
}
