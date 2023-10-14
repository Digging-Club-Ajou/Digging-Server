package server.controller.melody_card;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
                .andDo(document("회원의 멜로디 카드 리스트 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멜로디 카드")
                                .summary("회원의 멜로디 카드 리스트 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("memberId").type(NUMBER).description("회원 id"),
                                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                                        fieldWithPath("singer").type(STRING).description("가수"),
                                        fieldWithPath("songTitle").type(STRING).description("노래 제목"),
                                        fieldWithPath("imageUrl").type(STRING).description("이미지 url"),
                                        fieldWithPath("streamingLink").type(STRING).description("스트리밍 url"),
                                        fieldWithPath("address").type(STRING).description("주소"),
                                        fieldWithPath("cardDescription").type(STRING).description("카드 설명"),
                                        fieldWithPath("color").type(STRING).description("색상")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 멜로디 카드 정보를 가져옵니다")
    void getMelodyCardImages() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/melody-cards")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("멜로디 카드 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("멜로디 카드")
                                .summary("로그인한 회원의 모든 멜로디 카드 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("melodyCardResponses[0].memberId").type(NUMBER)
                                                .description("회원 id"),
                                        fieldWithPath("melodyCardResponses[0].nickname").type(STRING)
                                                .description("닉네임"),
                                        fieldWithPath("melodyCardResponses[0].singer").type(STRING)
                                                .description("가수"),
                                        fieldWithPath("melodyCardResponses[0].songTitle").type(STRING)
                                                .description("노래 제목"),
                                        fieldWithPath("melodyCardResponses[0].imageUrl").type(STRING)
                                                .description("이미지 url"),
                                        fieldWithPath("melodyCardResponses[0].streamingLink").type(STRING)
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
