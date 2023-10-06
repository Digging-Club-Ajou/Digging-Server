package server.repository.album;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.album.Album;

public interface AlbumJpaRepository extends JpaRepository<Album, Long> {

    boolean existsByMemberId(final long memberId);

    boolean existsByAlbumName(final String albumName);
}
