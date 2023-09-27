package server.repository.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.profile.Profile;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {
}
