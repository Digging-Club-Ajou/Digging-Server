package server.controller.member_search;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.util.TestConstant.*;

public class MemberSearchControllerTest extends ControllerTest {

    @Test
    @DisplayName("nickname 검색 결과 리스트를 가져옵니다.")
    void getMemberSearchList() throws Exception {


        String accessToken = login();


        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .nickname(TEST_NICKNAME1.value)
                .build();

        memberRepository.save(member);

        Member member1 = Member.builder()
                .username(TEST_USERNAME.value)
                .nickname(TEST_NICKNAME2.value)
                .build();

        memberRepository.save(member1);

        Album album1 = Album.builder()
                .memberId(member.getId())
                .nickname(TEST_NICKNAME.value)
                .albumName("앨범 이름1")
                .build();

        Album album2 = Album.builder()
                .memberId(member1.getId())
                .nickname(TEST_NICKNAME.value)
                .albumName("앨범 이름2")
                .build();

        albumRepository.save(album1);
        albumRepository.save(album2);




        // expected
        mockMvc.perform(get("/api/member/search")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .param("keyword", "test")
                )
                .andExpect(status().isOk())
                .andDo(document("nickname 검색 결과 리스트 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("검색")
                                .summary("nickname 검색 리스트")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .queryParameters(
                                        parameterWithName("keyword").description("검색 keyword")
                                )
                                .build()
                        )));
    }
}
