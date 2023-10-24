package server.controller.card_favorite;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.card_favorite.CardFavorite;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.card_favorite.dto.CardFavoriteRequest;
import server.util.ControllerTest;


import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.util.TestConstant.TEST_USERNAME;

class CardFavoriteControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인한 회원이 특정 멜로디 카드에 감정 표현을 합니다")
    void changeLikesState() throws Exception {
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
        MelodyCard melodyCard = MelodyCard.builder()
                .memberId(member.getId())
                .build();

        melodyCardRepository.save(melodyCard);

        CardFavoriteRequest cardFavoriteRequest = new CardFavoriteRequest(melodyCard.getId(), true);

        // expected
        mockMvc.perform(post("/api/card-favorites")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardFavoriteRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원이 특정 멜로디 카드에 감정 표현",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("좋아요")
                                .summary("좋아요 or 좋아요 취소")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("melodyCardId").type(NUMBER).description("멜로디 카드 id"),
                                        fieldWithPath("isLike").type(BOOLEAN).description("좋아요")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 좋아요 목록을 가져옵니다")
    void findAll() throws Exception {
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
        CardFavorite cardFavorite1 = CardFavorite.builder()
                .memberId(member.getId())
                .build();

        CardFavorite cardFavorite2 = CardFavorite.builder()
                .memberId(member.getId())
                .build();

        CardFavorite cardFavorite3 = CardFavorite.builder()
                .memberId(member.getId())
                .build();

        cardFavoriteRepository.save(cardFavorite1);
        cardFavoriteRepository.save(cardFavorite2);
        cardFavoriteRepository.save(cardFavorite3);

        // expected
        mockMvc.perform(get("/api/card-favorites")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 좋아요 목록을 가져옴",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("좋아요")
                                .summary("좋아요 목록 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("cardFavoriteResponses[].melodyCardId").description("멜로디 카드 id"),
                                        fieldWithPath("cardFavoriteResponses[].songTitle").description("노래 제목"),
                                        fieldWithPath("cardFavoriteResponses[].artistName").description("가수 이름"),
                                        fieldWithPath("cardFavoriteResponses[].imageUrl").description("이미지 사진")
                                )
                                .build()
                        )));
    }
}