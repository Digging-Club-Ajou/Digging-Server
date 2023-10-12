package server.controller.location;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.mapper.genre.dto.GenreRequest;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;

class LocationControllerTest extends ControllerTest {

    @Test
    @DisplayName("현재 위치와 검색어로 가까운 장소를 찾습니다")
    void findLocation() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/location")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .param("query", "아주대학교 도서관")
                        .param("x", "127.044128202435")
                        .param("y", "37.283192168869")
                )
                .andExpect(status().isOk())
                .andDo(document("검색어로 장소 찾기 - 거리 가까운 순서",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("장소 정보")
                                .summary("장소 찾기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .queryParameters(
                                        parameterWithName("query").description("현재 위치 도로명 주소"),
                                        parameterWithName("x").description("경도"),
                                        parameterWithName("y").description("위도")
                                )
                                .responseFields(
                                        fieldWithPath("locationResponses[0].placeName").type(STRING)
                                                .description("장소 이름"),
                                        fieldWithPath("locationResponses[0].distance").type(STRING)
                                                .description("거리")
                                )
                                .build()
                        )));
    }
}