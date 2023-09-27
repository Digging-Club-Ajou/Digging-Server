package server.service.profile;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.profile.Profile;
import server.mapper.profile.ProfileMapper;
import server.repository.profile.ProfileRepository;

@Service
public class ProfileInfoSaveService {

    private final ProfileRepository profileRepository;

    public ProfileInfoSaveService(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional
    public void saveProfileInfo(final long memberId) {
        Profile profile = ProfileMapper.toEntity(memberId);
        profileRepository.save(profile);
    }
}
