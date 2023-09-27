package server.repository.profile;

import org.springframework.stereotype.Repository;
import server.domain.profile.Profile;

@Repository
public class ProfileRepository {

    private final ProfileJpaRepository profileJpaRepository;

    public ProfileRepository(final ProfileJpaRepository profileJpaRepository) {
        this.profileJpaRepository = profileJpaRepository;
    }

    public void save(final Profile profile) {
        profileJpaRepository.save(profile);
    }
}
