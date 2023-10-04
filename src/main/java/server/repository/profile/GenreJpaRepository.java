package server.repository.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.profile.Genre;

public interface GenreJpaRepository extends JpaRepository<Genre, Long> {
}
