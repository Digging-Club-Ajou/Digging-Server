package server.controller.genre;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.domain.music_recommentdation.MusicRecommendation;
import server.mapper.genre.dto.GenreRequest;
import server.mapper.member.dto.NicknameRequest;
import server.mapper.music_recommendation.dto.ArtistRequest;
import server.util.ControllerTest;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.global.constant.TimeConstant.ONE_MONTH;
import static server.util.TestConstant.TEST_USERNAME;

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

    @Test
    @DisplayName("로그인한 회원의 가장 좋아하는 아티스트를 찾습니다")
    void findFavorite() throws Exception {
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

        // given 2
        String artistName = "NewJeans";
        MusicRecommendation musicRecommendation = MusicRecommendation.builder()
                .memberId(member.getId())
                .artistName(artistName)
                .build();

        musicRecommendationRepository.save(musicRecommendation);

        // expected
        mockMvc.perform(get("/api/favorite-genres")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 관심 있는 장르 반환",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("장르")
                                .summary("로그인한 회원의 관심 있는 장르 반환")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("genre").type(STRING).description("관심있는 장르")
                                )
                                .build()
                        )));
    }
}