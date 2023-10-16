package server.repository.album;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.album.Album;

import java.util.Optional;

public interface AlbumJpaRepository extends JpaRepository<Album, Long> {

    boolean existsByMemberId(final long memberId);

    boolean existsById(final long albumId);

    boolean existsByAlbumName(final String albumName);

    Optional<Album> findByMemberId(final long memberId);

    Album getByMemberId(final long memberId);
}
