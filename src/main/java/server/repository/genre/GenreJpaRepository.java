package server.repository.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.genre.Genre;

public interface GenreJpaRepository extends JpaRepository<Genre, Long> {
}
