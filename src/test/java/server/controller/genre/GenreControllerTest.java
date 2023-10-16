package server.controller.genre;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.mapper.genre.dto.GenreRequest;
import server.mapper.member.dto.NicknameRequest;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;

class GenreControllerTest extends ControllerTest {

    @Test
    @DisplayName("좋아하는 장르 5개 선택 후 장르를 저장합니다")
    void createGenre() throws Exception {
        // given
        String accessToken = login();

        GenreRequest genreRequest = new GenreRequest(true, true, true, true,
                true, false, false, false, false);

        // expected
        mockMvc.perform(post("/api/genres")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("장르 등록 - 성공",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("장르")
                                .summary("장르 등록")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("ballade").type(BOOLEAN).description("발라드"),
                                        fieldWithPath("dance").type(BOOLEAN).description("댄스"),
                                        fieldWithPath("rockMetal").type(BOOLEAN).description("록/메탈"),
                                        fieldWithPath("rbAndSoul").type(BOOLEAN).description("R&B/soul"),
                                        fieldWithPath("rapHiphop").type(BOOLEAN).description("랩/힙합"),
                                        fieldWithPath("folkBlues").type(BOOLEAN).description("포크/블루스"),
                                        fieldWithPath("indie").type(BOOLEAN).description("인디"),
                                        fieldWithPath("pop").type(BOOLEAN).description("팝"),
                                        fieldWithPath("ostAndMusical").type(BOOLEAN).description("OST/뮤지컬")
                                )
                                .build()
                        )));
    }
}