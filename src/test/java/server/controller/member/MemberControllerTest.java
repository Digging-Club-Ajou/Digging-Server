package server.controller.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.member.vo.Gender;
import server.mapper.member.dto.MemberSignupRequest;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.util.TestConstant.*;

class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("회원가입에 실패합니다")
    void signupFail() throws Exception {
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
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("회원가입 실패 (비밀번호가 일치하지 않음)",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원가입")
                                .requestFields(
                                        fieldWithPath("username").type(STRING).description("동영상 정보 id"),
                                        fieldWithPath("loginId").type(STRING).description("이전 리액션"),
                                        fieldWithPath("password").type(STRING).description("비밀번호"),
                                        fieldWithPath("passwordCheck").type(STRING).description("비밀번호 확인"),
                                        fieldWithPath("phoneNumber").type(STRING).description("전화번호"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("gender").type(STRING).description("성별")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("회원가입에 성공합니다")
    void signupSuccess() throws Exception {
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

        // expected
        mockMvc.perform(post("/api/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("회원가입 성공",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원가입")
                                .requestFields(
                                        fieldWithPath("username").type(STRING).description("동영상 정보 id"),
                                        fieldWithPath("loginId").type(STRING).description("이전 리액션"),
                                        fieldWithPath("password").type(STRING).description("비밀번호"),
                                        fieldWithPath("passwordCheck").type(STRING).description("비밀번호 확인"),
                                        fieldWithPath("phoneNumber").type(STRING).description("전화번호"),
                                        fieldWithPath("email").type(STRING).description("이메일"),
                                        fieldWithPath("gender").type(STRING).description("성별")
                                )
                                .build()
                        )));
    }
}