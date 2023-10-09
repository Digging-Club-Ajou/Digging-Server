package server.controller.spotify;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;

class SpotifyControllerTest extends ControllerTest {

    @Test
    @DisplayName("아티스트 이름이나 노래 제목으로 노래 리스트를 가져옵니다")
    void validateNicknameDuplicationFail() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/musics")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .param("search", "NewJeans")
                )
                .andExpect(status().isOk())
                .andDo(document("음악 검색 - 아티스트 or 노래 제목으로 검색",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("음악")
                                .summary("음악 검색")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .queryParameters(
                                        parameterWithName("search").description("검색어 - 아티스트 이름 or 노래 제목")
                                )
                                .responseFields(
                                        fieldWithPath("spotifySearchDtos[0].artistName").type(STRING)
                                                .description("아티스트 이름"),
                                        fieldWithPath("spotifySearchDtos[0].songTitle").type(STRING)
                                                .description("노래 제목"),
                                        fieldWithPath("spotifySearchDtos[0].imageUrl").type(STRING)
                                                .description("노래 이미지 URL")
                                )
                                .build()
                        )));
    }

}