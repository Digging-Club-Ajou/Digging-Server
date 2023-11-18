package server.controller.card_favorite;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.album.Album;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.util.TestConstant.TEST_USERNAME;

class CardFavoriteControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인한 회원이 특정 멜로디 카드에 좋아요를 누릅니다")
    void saveFavorite() throws Exception {
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

        // expected
        mockMvc.perform(post("/api/card-favorites/likes/{melodyCardId}", melodyCard.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isNoContent())
                .andDo(document("로그인한 회원이 특정 멜로디 카드 좋아요 누르기",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("좋아요")
                                .summary("좋아요 누르기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
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

        Album album = Album.builder()
                .memberId(member.getId())
                .build();

        albumRepository.save(album);

        // given 2
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

        // given 3
        MelodyCard melodyCard1 = MelodyCard.builder()
                .memberId(member.getId())
                .albumId(album.getId())
                .artistName("아티스트 이름 1")
                .songTitle("노래 제목 1")
                .albumCoverImageUrl("https://test-album-cover-image-url1")
                .build();

        MelodyCard melodyCard2 = MelodyCard.builder()
                .memberId(member.getId())
                .albumId(album.getId())
                .artistName("아티스트 이름 2")
                .songTitle("노래 제목 2")
                .albumCoverImageUrl("https://test-album-cover-image-url2")
                .build();

        MelodyCard melodyCard3 = MelodyCard.builder()
                .memberId(member.getId())
                .albumId(album.getId())
                .artistName("아티스트 이름 3")
                .songTitle("노래 제목 3")
                .albumCoverImageUrl("https://test-album-cover-image-url3")
                .build();

        melodyCardRepository.save(melodyCard1);
        melodyCardRepository.save(melodyCard2);
        melodyCardRepository.save(melodyCard3);

        // given 4
        CardFavorite cardFavorite1 = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard1.getId())
                .build();

        CardFavorite cardFavorite2 = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard2.getId())
                .build();

        CardFavorite cardFavorite3 = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard3.getId())
                .build();

        cardFavoriteRepository.save(cardFavorite1);
        cardFavoriteRepository.save(cardFavorite2);
        cardFavoriteRepository.save(cardFavorite3);


        // expected
        mockMvc.perform(get("/api/card-favorites")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 좋아요 목록 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("좋아요")
                                .summary("로그인한 회원의 좋아요 목록 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("cardFavoriteResponses[].melodyCardId").description("멜로디 카드 id"),
                                        fieldWithPath("cardFavoriteResponses[].songTitle").description("노래 제목"),
                                        fieldWithPath("cardFavoriteResponses[].artistName").description("가수 이름"),
                                        fieldWithPath("cardFavoriteResponses[].albumCoverImageUrl")
                                                .description("앨범 커버 이미지 url")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("회원 id로 좋아요 목록을 가져옵니다")
    void findAllByMemberId() throws Exception {
        // given 1
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        Album album = Album.builder()
                .memberId(member.getId())
                .build();

        albumRepository.save(album);

        // given 2
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

        // given 3
        MelodyCard melodyCard1 = MelodyCard.builder()
                .memberId(member.getId())
                .albumId(album.getId())
                .artistName("아티스트 이름 1")
                .songTitle("노래 제목 1")
                .albumCoverImageUrl("https://test-album-cover-image-url1")
                .build();

        MelodyCard melodyCard2 = MelodyCard.builder()
                .memberId(member.getId())
                .albumId(album.getId())
                .artistName("아티스트 이름 2")
                .songTitle("노래 제목 2")
                .albumCoverImageUrl("https://test-album-cover-image-url2")
                .build();

        MelodyCard melodyCard3 = MelodyCard.builder()
                .memberId(member.getId())
                .albumId(album.getId())
                .artistName("아티스트 이름 3")
                .songTitle("노래 제목 3")
                .albumCoverImageUrl("https://test-album-cover-image-url3")
                .build();

        melodyCardRepository.save(melodyCard1);
        melodyCardRepository.save(melodyCard2);
        melodyCardRepository.save(melodyCard3);

        // given 4
        CardFavorite cardFavorite1 = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard1.getId())
                .build();

        CardFavorite cardFavorite2 = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard2.getId())
                .build();

        CardFavorite cardFavorite3 = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard3.getId())
                .build();

        cardFavoriteRepository.save(cardFavorite1);
        cardFavoriteRepository.save(cardFavorite2);
        cardFavoriteRepository.save(cardFavorite3);

        // expected
        mockMvc.perform(get("/api/card-favorites/members/{memberId}", member.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("특정 회원의 좋아요 목록 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("좋아요")
                                .summary("특정 회원의 좋아요 목록 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("cardFavoriteResponses[].melodyCardId").description("멜로디 카드 id"),
                                        fieldWithPath("cardFavoriteResponses[].songTitle").description("노래 제목"),
                                        fieldWithPath("cardFavoriteResponses[].artistName").description("가수 이름"),
                                        fieldWithPath("cardFavoriteResponses[].albumCoverImageUrl")
                                                .description("앨범 커버 이미지 url")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 멜로디 카드에 대한 좋아요 상태를 가져옵니다")
    void findLikeInfo() throws Exception {
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

        CardFavorite cardFavorite = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard.getId())
                .build();

        cardFavoriteRepository.save(cardFavorite);

        // expected
        mockMvc.perform(get("/api/card-favorites/likes/{melodyCardId}", melodyCard.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("특정 멜로디 카드에 대한 좋아요 상태 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("좋아요")
                                .summary("좋아요 상태 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("isLike").description("좋아요 상태")
                                )
                                .build()
                        )));
    }


    @Test
    @DisplayName("멜로디카드 Id를 이용하여 로그인한 회원의 좋아요 멜로디 카드를 삭제함 ")
    void deleteFavorite() throws Exception {
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

        CardFavorite cardFavorite = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard.getId())
                .build();

        cardFavoriteRepository.save(cardFavorite);

        // expected
        mockMvc.perform(delete("/api/card-favorites/likes/{melodyCardId}", melodyCard.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isNoContent())
                .andDo(document("로그인한 회원의 특정 좋아요 멜로디 카드 삭제",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("좋아요")
                                .summary("좋아요 삭제하기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .build()
                        )));
    }
}