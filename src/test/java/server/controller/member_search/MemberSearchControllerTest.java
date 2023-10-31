//package server.controller.member_search;
//
//import com.epages.restdocs.apispec.ResourceSnippetParameters;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import server.domain.card_favorite.CardFavorite;
//import server.domain.member.persist.Member;
//import server.domain.member.vo.MemberSession;
//import server.mapper.memeber_search.dto.MemberSearchRequest;
//import server.util.ControllerTest;
//
//import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
//import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static server.global.constant.TextConstant.ACCESS_TOKEN;
//import static server.global.constant.TimeConstant.ONE_HOUR;
//import static server.util.TestConstant.TEST_USERNAME;
//
//public class MemberSearchControllerTest extends ControllerTest {
//
//    @Test
//    @DisplayName("nickname 검색 결과 리스트를 가져옵니다.")
//    void findAll() throws Exception {
//
//
//        String accessToken = login();
//        MemberSearchRequest memberSearchRequest = new MemberSearchRequest("l");
//
//
//        // expected
//        mockMvc.perform(get("/api/member/search")
//                        .header(ACCESS_TOKEN.value, accessToken)
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(memberSearchRequest))
//                )
//                .andExpect(status().isOk())
//                .andDo(document("nickname 검색 결과 리스트 가져오기",
//                        preprocessResponse(prettyPrint()),
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("검색")
//                                .summary("nickname 검색 리스트")
//                                .requestHeaders(
//                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
//                                )
//                                .requestFields(
//                                        fieldWithPath("keyword").description("키워드")
//                                )
//                                .responseFields(
//                                        fieldWithPath("cardFavoriteResponses[].imageUrl").description("imageUrl"),
//                                        fieldWithPath("cardFavoriteResponses[].nickname").description("닉네임")
//                                )
//                                .build()
//                        )));
//    }
//}
