package server.mapper.profile;

import server.domain.profile.Profile;

public class ProfileMapper {

    private ProfileMapper() {
    }

    public static Profile toEntity(final long memberId) {
        return Profile.builder()
                .memberId(memberId)
                .build();
    }
}
