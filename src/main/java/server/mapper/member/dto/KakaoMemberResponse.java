package server.mapper.member.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import server.domain.member.persist.Member;
import server.domain.member.vo.Gender;

import java.time.LocalDateTime;

import static server.domain.member.vo.LoginType.KAKAO;

public record KakaoMemberResponse(Long id,
                                  boolean hasSignedUp,
                                  LocalDateTime connectedAt,
                                  KakaoAccount kakaoAccount) {

    public Member extract() {
        Gender gender;
        if(kakaoAccount().gender == "FEMALE")
            gender = Gender.FEMALE;
        else
            gender = Gender.MALE;

        return Member.builder()
                .nickname(kakaoAccount().profile.nickname).email(kakaoAccount().email).loginId(String.valueOf(id)).gender(gender).build();

    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record KakaoAccount(
            boolean profileNeedsAgreement,
            boolean profileNicknameNeedsAgreement,
            boolean profileImageNeedsAgreement,
            Profile profile,
            boolean nameNeedsAgreement,
            String name,
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            String email,
            boolean ageRangeNeedsAgreement,
            String ageRange,
            boolean birthyearNeedsAgreement,
            String birthyear,
            boolean birthdayNeedsAgreement,
            String birthday,
            String birthdayType,
            boolean genderNeedsAgreement,
            String gender,
            boolean phoneNumberNeedsAgreement,
            String phoneNumber,
            boolean ciNeedsAgreement,
            String ci,
            LocalDateTime ciAuthenticatedAt
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record Profile(
            String nickname,
            String thumbnailImageUrl,
            String profileImageUrl,
            boolean isDefaultImage
    ) {
    }
}
