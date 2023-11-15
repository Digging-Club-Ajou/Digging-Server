package server.repository.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.genre.Genre;

import java.util.Optional;

public interface GenreJpaRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByMemberId(final long memberId);
}
