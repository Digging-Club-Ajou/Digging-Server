package server.service.oauth;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import server.domain.member.persist.Member;
import server.domain.member.vo.Gender;
import server.mapper.member.dto.KakaoSignupRequest;
import server.service.member.DiggingLoginService;
import server.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;
import static server.global.constant.TextConstant.*;
import static server.util.TestConstant.*;

class DiggingLoginServiceTest extends ServiceTest {

    @Autowired
    private DiggingLoginService diggingLoginService;

    @Test
    @DisplayName("이미 가입된 회원은 회원가입 없이 바로 로그인을 합니다")
    void alreadyKakaoLogin() {
        // given 1
        memberRepository.save(Member
                .builder()
                .email(TEST_EMAIL.value)
                .build()
        );

        // given 2
        KakaoSignupRequest dto = KakaoSignupRequest.builder()
                .email(TEST_EMAIL.value)
                .phoneNumber(TEST_PHONE_NUMBER.value)
                .gender(Gender.UNKNOWN)
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        diggingLoginService.kakaoLogin(dto);
        Cookie cookie = response.getCookie(REFRESH_TOKEN.value);

        // then
        assertThat(memberRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("카카오 정보를 받아와서 회원 가입 후 로그인을 합니다")
    void kakaoLogin() {
        // given
        KakaoSignupRequest dto = KakaoSignupRequest.builder()
                .email(TEST_EMAIL.value)
                .phoneNumber(TEST_PHONE_NUMBER.value)
                .gender(Gender.UNKNOWN)
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        diggingLoginService.kakaoLogin(dto);

        // then
        assertThat(memberRepository.count()).isEqualTo(1);
    }
}