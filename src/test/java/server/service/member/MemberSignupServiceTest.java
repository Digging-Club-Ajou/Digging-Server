package server.service.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.member.vo.Gender;
import server.global.exception.BadRequestException;
import server.mapper.member.dto.MemberSignupRequest;
import server.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;
import static server.util.TestConstant.*;
import static server.util.TestConstant.TEST_EMAIL;

class MemberSignupServiceTest extends ServiceTest {

    @Autowired
    private MemberSignupService memberSignupService;

    @Test
    @DisplayName("비밀번호 확인시 일치하지 않으면 회원가입에 실패합니다")
    void signupFail() {
        // given
        MemberSignupRequest dto = MemberSignupRequest.builder()
                .username(TEST_USERNAME.value)
                .loginId(TEST_LOGIN_ID.value)
                .password(TEST_PASSWORD.value)
                .passwordCheck("일치하지 않는 비밀번호")
                .phoneNumber(TEST_PHONE_NUMBER.value)
                .email(TEST_EMAIL.value)
                .gender(Gender.MALE)
                .build();

        // expected
        assertThatThrownBy(() -> memberSignupService.signup(dto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("비밀번호 확인시 일치하면 회원가입에 성공합니다")
    void signupSuccess() {
        // given
        MemberSignupRequest dto = MemberSignupRequest.builder()
                .username(TEST_USERNAME.value)
                .loginId(TEST_LOGIN_ID.value)
                .password(TEST_PASSWORD.value)
                .passwordCheck(TEST_PASSWORD.value)
                .phoneNumber(TEST_PHONE_NUMBER.value)
                .email(TEST_EMAIL.value)
                .gender(Gender.MALE)
                .build();

        // when
        memberSignupService.signup(dto);

        // expected
        assertThat(memberRepository.count()).isEqualTo(1);
    }
}