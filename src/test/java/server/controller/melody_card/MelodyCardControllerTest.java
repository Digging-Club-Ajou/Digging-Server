package server.controller.melody_card;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.following.dto.FollowingInfoDto;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static java.sql.JDBCType.BOOLEAN;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.global.constant.TimeConstant.ONE_MONTH;
import static server.util.TestConstant.TEST_NICKNAME;
import static server.util.TestConstant.TEST_USERNAME;

public class MelodyCardControllerTest extends ControllerTest {

    @Test
    @DisplayName("멜로디 카드 id로 멜로디 카드 정보를 가져옵니다")
    void getMelodyCard() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/melody-cards/{melodyCardId}", 1)
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("특정 멜로디 카드 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멜로디 카드")
                                .summary("특정 멜로디 카드 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("melodyCardId").type(NUMBER).description("멜로디 카드 id"),
                                        fieldWithPath("albumId").type(NUMBER).description("앨범 id"),
                                        fieldWithPath("memberId").type(NUMBER).description("회원 id"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("artistName").type(STRING).description("가수"),
                                        fieldWithPath("songTitle").type(STRING).description("노래 제목"),
                                        fieldWithPath("previewUrl").type(STRING).description("스트리밍 url"),
                                        fieldWithPath("imageUrl").type(STRING).description("이미지 url"),
                                        fieldWithPath("albumCoverImageUrl").type(STRING).description("앨범 커버 이미지 url"),
                                        fieldWithPath("address").type(STRING).description("주소"),
                                        fieldWithPath("cardDescription").type(STRING).description("카드 설명"),
                                        fieldWithPath("color").type(STRING).description("색상"),
                                        fieldWithPath("isImageUrl").type(BOOLEAN).description("imageUrl 유무")
                                        )
                                .build()
                        )));
    }

    @Test
    @DisplayName("회원의 멜로디 카드 정보를 가져옵니다")
    void getMelodyCardSuccess() throws Exception {
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

        MockHttpServletResponse response = new MockHttpServletResponse();

        // given 3
        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);
        String refreshToken = jwtFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
        jwtFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
        jwtFacade.setHeader(response, accessToken);

        // expected
        mockMvc.perform(get("/api/melody-cards/albums/{albumId}", album.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("특정 앨범의 모든 멜로디 카드 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멜로디 카드")
                                .summary("특정 앨범의 모든 멜로디 카드 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("melodyCardResponses[0].melodyCardId").type(NUMBER)
                                                .description("멜로디 카드 id"),
                                        fieldWithPath("melodyCardResponses[0].albumId").type(NUMBER)
                                                .description("앨범 id"),
                                        fieldWithPath("melodyCardResponses[0].memberId").type(NUMBER)
                                                .description("회원 id"),
                                        fieldWithPath("melodyCardResponses[0].nickname").type(STRING)
                                                .description("닉네임"),
                                        fieldWithPath("melodyCardResponses[0].artistName").type(STRING)
                                                .description("가수"),
                                        fieldWithPath("melodyCardResponses[0].songTitle").type(STRING)
                                                .description("노래 제목"),
                                        fieldWithPath("melodyCardResponses[0].imageUrl").type(STRING)
                                                .description("이미지 url"),
                                        fieldWithPath("melodyCardResponses[0].previewUrl").type(STRING)
                                                .description("스트리밍 url"),
                                        fieldWithPath("melodyCardResponses[0].albumCoverImageUrl").type(STRING)
                                                .description("앨범 커버 이미지 url"),
                                        fieldWithPath("melodyCardResponses[0].address").type(STRING)
                                                .description("주소"),
                                        fieldWithPath("melodyCardResponses[0].cardDescription").type(STRING)
                                                .description("카드 설명"),
                                        fieldWithPath("melodyCardResponses[0].color").type(STRING)
                                                .description("색상"),
                                        fieldWithPath("melodyCardResponses[0].isImageUrl").type(BOOLEAN)
                                                .description("이미지 URL 유무")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인 한 회원의 멜로디 카드 정보를 가져옵니다")
    void getMelodyCardByLoginSuccess() throws Exception {
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

        MockHttpServletResponse response = new MockHttpServletResponse();

        // given 3
        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);
        String refreshToken = jwtFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
        jwtFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
        jwtFacade.setHeader(response, accessToken);

        // expected
        mockMvc.perform(get("/api/melody-cards")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 모든 멜로디 카드 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멜로디 카드")
                                .summary("로그인한 회원의 모든 멜로디 카드 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("melodyCardResponses[0].melodyCardId").type(NUMBER)
                                                .description("멜로디 카드 id"),
                                        fieldWithPath("melodyCardResponses[0].albumId").type(NUMBER)
                                                .description("앨범 id"),
                                        fieldWithPath("melodyCardResponses[0].memberId").type(NUMBER)
                                                .description("회원 id"),
                                        fieldWithPath("melodyCardResponses[0].nickname").type(STRING)
                                                .description("닉네임"),
                                        fieldWithPath("melodyCardResponses[0].artistName").type(STRING)
                                                .description("가수"),
                                        fieldWithPath("melodyCardResponses[0].songTitle").type(STRING)
                                                .description("노래 제목"),
                                        fieldWithPath("melodyCardResponses[0].imageUrl").type(STRING)
                                                .description("이미지 url"),
                                        fieldWithPath("melodyCardResponses[0].previewUrl").type(STRING)
                                                .description("스트리밍 url"),
                                        fieldWithPath("melodyCardResponses[0].albumCoverImageUrl").type(STRING)
                                                .description("앨범 커버 이미지 url"),
                                        fieldWithPath("melodyCardResponses[0].address").type(STRING)
                                                .description("주소"),
                                        fieldWithPath("melodyCardResponses[0].cardDescription").type(STRING)
                                                .description("카드 설명"),
                                        fieldWithPath("melodyCardResponses[0].color").type(STRING)
                                                .description("색상"),
                                        fieldWithPath("melodyCardResponses[0].isImageUrl").type(BOOLEAN)
                                                .description("imageUrl 유무")
                                )
                                .build()
                        )));
    }


    @Test
    @DisplayName("멜로디 카드가 존재하지 않으면 404를 반환합니다.")
    void deleteFollowing() throws Exception {
        // given 1
        Member member = Member.builder()
                .nickname(TEST_NICKNAME.value)
                .build();

        memberRepository.save(member);

        Album album = Album.builder()
                .memberId(member.getId())
                .build();

        albumRepository.save(album);

        MelodyCard melodyCard = MelodyCard.builder().memberId(member.getId()).albumId(album.getId()).build();

        melodyCardRepository.save(melodyCard);

        // given 2
        String accessToken = login();


        // expected
        mockMvc.perform(delete("/api/melody-cards/{melodyCardId}",melodyCard.getId())
                        .header(ACCESS_TOKEN.value, accessToken)

                )
                .andExpect(status().isNoContent())
                .andDo(document("멜로디 카드 삭제 ",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멜로디 카드")
                                .summary("멜로디 카드 삭제")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .build()
                        )));
    }
}
