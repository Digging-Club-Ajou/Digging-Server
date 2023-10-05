package server.repository.album;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.profile.Album;

public interface AlbumJpaRepository extends JpaRepository<Album, Long> {

    boolean existsByMemberId(final long memberId);
}
