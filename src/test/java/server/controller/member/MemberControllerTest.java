package server.controller.member;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.domain.member.vo.Gender;
import server.domain.member.vo.MemberSession;
import server.mapper.member.dto.NicknameRequest;
import server.mapper.member.dto.UserInfoRequest;
import server.util.ControllerTest;

import java.sql.Date;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
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
                .andExpect(status().isNoContent())
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
    @DisplayName("로그인한 사용자의 회원 정보를 가져옵니다")
    void getMemberResponse() throws Exception {
        // given 1
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        Album album = Album.builder()
                .memberId(member.getId())
                .build();

        albumRepository.save(album);

        // given 3
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

        // expected
        mockMvc.perform(get("/api/members")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("회원정보 가져오기 성공",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("회원 정보 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("회원 ID"),
                                        fieldWithPath("albumId").type(NUMBER).description("앨범 ID")
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
    @DisplayName("로그인한 유저 정보를 저장합니다.")
    void createMemberInfo() throws Exception {

        // given 1
        Member member = Member.builder()
                .nickname(TEST_NICKNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        String accessToken = login();

        UserInfoRequest dto = new UserInfoRequest(Gender.MALE, new Date(2000-01-01));

        // expected
        mockMvc.perform(post("/api/memberInfo")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent())
                .andDo(document("로그인한 유저 정보(성별과 생일) 저장",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("유저 정보")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("gender").description("성별, 형식(FEMALE,MALE)"),
                                        fieldWithPath("birthDate").description("생일")
                                )
                                .build()
                        )));
    }
}