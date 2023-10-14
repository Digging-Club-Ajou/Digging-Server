package server.repository.album;

import org.springframework.stereotype.Repository;
import server.domain.album.Album;
import server.global.constant.ExceptionMessage;
import server.global.exception.BadRequestException;

import java.util.Optional;

import static server.global.constant.ExceptionMessage.ALBUM_NOT_EXIST;

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

    public Album getByMemberId(final long memberId){
        return albumJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BadRequestException(ALBUM_NOT_EXIST.message));
    }
}
