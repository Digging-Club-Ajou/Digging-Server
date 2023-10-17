package server.controller.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.member.persist.Member;
import server.mapper.member.dto.NicknameRequest;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.util.TestConstant.*;

class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("닉네임 검증을 했을 때 형식이 맞지 않으면 예외가 발생합니다")
    void validateNicknameRegexFail() throws Exception {
        // given
        String accessToken = login();

        NicknameRequest dto = new NicknameRequest("형식이 맞지않음!@#");

        // expected
        mockMvc.perform(post("/api/nickname-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("닉네임 검증 - 잘못된 형식의 닉네임",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("닉네임 검증")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("닉네임 검증을 했을 때 이미 존재하는 닉네임이면 예외가 발생합니다")
    void validateNicknameDuplicationFail() throws Exception {
        // given 1
        Member member = Member.builder()
                .nickname(TEST_NICKNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        String accessToken = login();

        NicknameRequest dto = new NicknameRequest(TEST_NICKNAME.value);

        // expected
        mockMvc.perform(post("/api/nickname-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("닉네임 검증 - 중복된 닉네임",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("닉네임 검증")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("닉네임 검증을 했을 때 사용가능하면 true를 반환합니다")
    void validateNicknameDuplicationSuccess() throws Exception {
        // given
        String accessToken = login();

        NicknameRequest dto = new NicknameRequest(TEST_NICKNAME.value);

        // expected
        mockMvc.perform(post("/api/nickname-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("닉네임 검증 - 사용 가능",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("닉네임 검증")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("사용 여부 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("이미 존재하는 닉네임은 생성할 수 없습니다")
    void createNicknameDuplicationFail() throws Exception {
        // given 1
        Member member = Member.builder()
                .nickname(TEST_NICKNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        String accessToken = login();

        NicknameRequest dto = new NicknameRequest(TEST_NICKNAME.value);

        // expected
        mockMvc.perform(post("/api/nickname")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("닉네임 생성 실패 - 중복된 닉네임",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("닉네임")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("닉네임의 구성요소로 영문, 숫자, 밑줄, 마침표 이외의 것이 들어가면 예외가 발생합니다")
    void createNicknameRegexpFail() throws Exception {
        // given
        String accessToken = login();
        NicknameRequest dto = new NicknameRequest("잘못된 형식의 닉네임 @!#");

        // expected
        mockMvc.perform(post("/api/nickname")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("닉네임 생성 실패 - 잘못된 형식의 닉네임",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("닉네임")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("닉네임의 구성요소가 조건에 맞으면 닉네임 생성에 성공합니다")
    void createNicknameSuccess() throws Exception {
        // given
        String accessToken = login();
        NicknameRequest dto = new NicknameRequest(TEST_NICKNAME.value);

        // expected
        mockMvc.perform(post("/api/nickname")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("닉네임 생성 성공",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("닉네임")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 닉네임 정보를 가져옵니다")
    void getNicknameSuccess() throws Exception {
        // given
        String accessToken = loginCreateName();

        // expected
        mockMvc.perform(get("/api/nickname")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("닉네임 가져오기 성공",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("닉네임")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("nickname").type(STRING).description("닉네임")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인하지 않으면 회원의 닉네임을 가져올 수 없습니다")
    void getNicknameFail() throws Exception {
        // expected
        mockMvc.perform(get("/api/nickname"))
                .andExpect(status().isUnauthorized())
                .andDo(document("닉네임 가져오기 실패",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("닉네임")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 이름을 가져옵니다")
    void getUsernameSuccess() throws Exception {
        // given
        String accessToken = loginCreateName();

        // expected
        mockMvc.perform(get("/api/username")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("회원 이름 가져오기 성공",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원 이름 (실명)")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("username").type(STRING).description("회원 이름")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인하지 않으면 회원의 이름을 가져올 수 없습니다")
    void getUsernameFail() throws Exception {
        // expected
        mockMvc.perform(get("/api/username"))
                .andExpect(status().isUnauthorized())
                .andDo(document("회원 이름 가져오기 실패",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원 이름 (실명)")
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }
}