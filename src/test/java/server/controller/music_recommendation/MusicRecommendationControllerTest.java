package server.controller.music_recommendation;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.mapper.music_recommendation.dto.ArtistRequest;
import server.util.ControllerTest;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;

class MusicRecommendationControllerTest extends ControllerTest {

    @Test
    @DisplayName("좋아하는 아티스트를 저장합니다")
    void createArtists() throws Exception {
        // given 1
        String accessToken = login();

        // given 2
        String artistName1 = "artistName1";
        String artistName2 = "artistName2";
        String artistName3 = "artistName3";
        String artistName4 = "artistName4";
        String artistName5 = "artistName5";

        List<String> artistNames = new ArrayList<>();
        artistNames.add(artistName1);
        artistNames.add(artistName2);
        artistNames.add(artistName3);
        artistNames.add(artistName4);
        artistNames.add(artistName5);

        ArtistRequest artistRequest = new ArtistRequest(artistNames);

        // expected
        mockMvc.perform(post("/api/artists")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artistRequest))
                )
                .andExpect(status().isNoContent())
                .andDo(document("좋아하는 아티스트 등록 - 성공",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("좋아하는 아티스트")
                                .summary("좋아하는 아티스트 등록")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("artistNames[]").type(ARRAY).description("좋아하는 아티스트 목록")
                                )
                                .build()
                        )));
    }
}