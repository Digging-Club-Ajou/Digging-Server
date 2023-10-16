package server.controller.melody_card;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.global.constant.TimeConstant.ONE_MONTH;
import static server.util.TestConstant.TEST_USERNAME;

public class MelodyCardControllerTest extends ControllerTest {

    @Test
    @DisplayName("멜로디 카드 id로 멜로디 카드 정보를 가져옵니다")
    void getMelodyCardImage() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/melody-cards/{melodyCardId}", 1)
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("멜로디 카드 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멜로디 카드")
                                .summary("멜로디 카드 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("회원 id"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("artistName").type(STRING).description("가수"),
                                        fieldWithPath("songTitle").type(STRING).description("노래 제목"),
                                        fieldWithPath("imageUrl").type(STRING).description("이미지 url"),
                                        fieldWithPath("previewUrl").type(STRING).description("스트리밍 url"),
                                        fieldWithPath("address").type(STRING).description("주소"),
                                        fieldWithPath("cardDescription").type(STRING).description("카드 설명"),
                                        fieldWithPath("color").type(STRING).description("색상")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("회원의 멜로디 카드 정보를 가져옵니다")
    void getMelodyCard() throws Exception {
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
        mockMvc.perform(get("/api/melody-cards/member/{memberId}", member.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("멜로디 카드 리스트 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멜로디 카드")
                                .summary("회원의 모든 멜로디 카드 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
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
                                        fieldWithPath("melodyCardResponses[0].address").type(STRING)
                                                .description("주소"),
                                        fieldWithPath("melodyCardResponses[0].cardDescription").type(STRING)
                                                .description("카드 설명"),
                                        fieldWithPath("melodyCardResponses[0].color").type(STRING)
                                                .description("색상")
                                )
                                .build()
                        )));
    }
}
