package server.controller.album;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.album.dto.AlbumNameRequest;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.util.TestConstant.TEST_NICKNAME;
import static server.util.TestConstant.TEST_USERNAME;


class AlbumControllerTest extends ControllerTest {

    @Test
    @DisplayName("형식에 맞지 않는 앨범 이름이면 예외가 발생합니다")
    void validateAlbumNameEmoticonFail() throws Exception {
        // given
        String accessToken = login();
        AlbumNameRequest dto = new AlbumNameRequest("특수문자, 이모티콘 사용@#$%");

        // expected
        mockMvc.perform(post("/api/albums/name-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("앨범 이름에 특수문자, 이모티콘 사용",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 이름 검증")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("형식에 맞지 않는 앨범 이름이면 예외가 발생합니다")
    void validateAlbumName() throws Exception {
        // given
        String accessToken = login();
        AlbumNameRequest dto = new AlbumNameRequest("글자 수가 15글자가 넘는 앨범 이름은 생성할 수 없음");

        // expected
        mockMvc.perform(post("/api/albums/name-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("앨범 이름 15글자 넘음",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 이름 검증")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 앨범이 존재하지 않으면 false를 반환합니다")
    void validateAlbumExistFalse() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(post("/api/albums-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 앨범이 존재하지 않음",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 존재 확인 (앨범 존재 여부 판단)")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("alreadyExist").type(BOOLEAN).description("앨범 존재")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 앨범이 존재하지 않으면 true를 반환합니다")
    void validateAlbumExistTrue() throws Exception {
        // given
        String accessToken = loginAndCreateAlbum();

        // expected
        mockMvc.perform(post("/api/albums-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 앨범이 존재함",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 존재 확인 (앨범 존재 여부 판단)")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("alreadyExist").type(BOOLEAN).description("앨범 존재")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("앨범 id가 존재하지 않으면 앨범 정보를 가져올 수 없습니다")
    void getAlbumFail() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/albums/{albumId}", 9999L)
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isNotFound())
                .andDo(document("앨범 정보 가져오기 - 실패",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 정보 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("앨범 id가 존재하면 앨범 정보를 가져올 수 있습니다")
    void getAlbumSuccess() throws Exception {
        // given
        String accessToken = login();

        Album album = Album.builder()
                .memberId(1L)
                .nickname(TEST_NICKNAME.value)
                .albumName("앨범 이름")
                .build();

        albumRepository.save(album);

        // expected
        mockMvc.perform(get("/api/albums/{albumId}", album.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("앨범 정보 가져오기 - 성공",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 정보 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("회원 id"),
                                        fieldWithPath("albumId").type(NUMBER).description("앨범 id"),
                                        fieldWithPath("nickname").type(STRING).description("회원 닉네임"),
                                        fieldWithPath("albumName").type(STRING).description("앨범명"),
                                        fieldWithPath("imageUrl").type(STRING).description("앨범 이미지 url"),
                                        fieldWithPath("artistNames[]").type(ARRAY).description("아티스트 이름 리스트")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 앨범 정보를 가져올 수 있습니다")
    void getLoginMemberAlbumSuccess() throws Exception {
        // given
        String accessToken = login();

        Album album = Album.builder()
                .memberId(1L)
                .nickname(TEST_NICKNAME.value)
                .albumName("앨범 이름")
                .build();

        albumRepository.save(album);

        // expected
        mockMvc.perform(get("/api/albums")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 앨범 정보 가져오기 - 성공",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("로그인한 회원의 앨범 정보 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("회원 id"),
                                        fieldWithPath("albumId").type(NUMBER).description("앨범 id"),
                                        fieldWithPath("nickname").type(STRING).description("회원 닉네임"),
                                        fieldWithPath("albumName").type(STRING).description("앨범명"),
                                        fieldWithPath("imageUrl").type(STRING).description("앨범 이미지 url"),
                                        fieldWithPath("artistNames[]").type(ARRAY).description("아티스트 이름 리스트")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원이 팔로잉한 앨범들의 정보를 가져옵니다")
    void getFollowedAlbums() throws Exception {
        // given 1
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

        // given 3
        Member member1 = Member.builder().build();
        Member member2 = Member.builder().build();
        Member member3 = Member.builder().build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        createAlbumInfo(member1, member2, member3);

        // given 4
        createFollowingInfo(member, member1, member2, member3);

        // expected
        mockMvc.perform(get("/api/albums/following")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 팔로잉한 앨범 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("팔로잉한 앨범 정보 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("albumResponses[].memberId").description("회원 id"),
                                        fieldWithPath("albumResponses[].albumId").description("앨범 id"),
                                        fieldWithPath("albumResponses[].nickname").description("닉네임"),
                                        fieldWithPath("albumResponses[].albumName").description("앨범 이름"),
                                        fieldWithPath("albumResponses[].imageUrl").description("앨범 이미지 url"),
                                        fieldWithPath("albumResponses[].artistNames").description("아티스트 이름")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 AI 추천 앨범들의 정보를 가져옵니다")
    void getAIRecommendationAlbums() throws Exception {
        // given 1
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

        // given 3
        Member member1 = Member.builder().build();
        Member member2 = Member.builder().build();
        Member member3 = Member.builder().build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        createAlbumInfo(member1, member2, member3);

        // expected
        mockMvc.perform(get("/api/albums/recommendation-ai")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 AI 추천 앨범 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("AI 추천 앨범 정보 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("albumResponses[].memberId").description("회원 id"),
                                        fieldWithPath("albumResponses[].albumId").description("앨범 id"),
                                        fieldWithPath("albumResponses[].nickname").description("닉네임"),
                                        fieldWithPath("albumResponses[].albumName").description("앨범 이름"),
                                        fieldWithPath("albumResponses[].imageUrl").description("앨범 이미지 url"),
                                        fieldWithPath("albumResponses[].artistNames").description("아티스트 이름")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 추천 장르 앨범들의 정보를 가져옵니다")
    void getGenreRecommendationAlbums() throws Exception {
        // given 1
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

        // given 3
        Member member1 = Member.builder().build();
        Member member2 = Member.builder().build();
        Member member3 = Member.builder().build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        createAlbumInfo(member1, member2, member3);

        // expected
        mockMvc.perform(get("/api/albums/recommendation-genres")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 추천 장르 앨범 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("추천 장르 앨범 정보 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("albumResponses[].memberId").description("회원 id"),
                                        fieldWithPath("albumResponses[].albumId").description("앨범 id"),
                                        fieldWithPath("albumResponses[].nickname").description("닉네임"),
                                        fieldWithPath("albumResponses[].albumName").description("앨범 이름"),
                                        fieldWithPath("albumResponses[].imageUrl").description("앨범 이미지 URL"),
                                        fieldWithPath("albumResponses[].artistNames").description("아티스트 이름")
                                )
                                .build()
                        )));
    }

    private void createFollowingInfo(final Member member, final Member member1, final Member member2, final Member member3) {
        FollowingInfo followingInfo1 = FollowingInfo.builder()
                .followedId(member1.getId())
                .followingId(member.getId())
                .build();

        FollowingInfo followingInfo2 = FollowingInfo.builder()
                .followedId(member2.getId())
                .followingId(member.getId())
                .build();

        FollowingInfo followingInfo3 = FollowingInfo.builder()
                .followedId(member3.getId())
                .followingId(member.getId())
                .build();

        followingRepository.save(followingInfo1);
        followingRepository.save(followingInfo2);
        followingRepository.save(followingInfo3);
    }

    private void createAlbumInfo(final Member member1, final Member member2, final Member member3) {
        Album album1 = Album.builder()
                .memberId(member1.getId())
                .nickname(TEST_NICKNAME.value)
                .albumName("앨범 이름1")
                .build();

        Album album2 = Album.builder()
                .memberId(member2.getId())
                .nickname(TEST_NICKNAME.value)
                .albumName("앨범 이름2")
                .build();


        Album album3 = Album.builder()
                .memberId(member3.getId())
                .nickname(TEST_NICKNAME.value)
                .albumName("앨범 이름2")
                .build();

        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);
    }
}