package server.controller.play_record;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.domain.play_record.PlayRecord;
import server.mapper.play_record.dto.PlayRecordRequest;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.util.TestConstant.TEST_USERNAME;

class PlayRecordControllerTest extends ControllerTest {

    @Test
    @DisplayName("유저 음악 재생 기록을 저장합니다")
    void savePlayRecord() throws Exception {
        // given
        String accessToken = login();
        PlayRecordRequest dto = new PlayRecordRequest("NewJeans", "Hype Boy");

        // expected
        mockMvc.perform(post("/api/musics/play-record")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("음악 재생 기록 저장",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("음악")
                                .summary("음악 재생 기록 저장")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("artistName").type(STRING).description("아티스트 이름"),
                                        fieldWithPath("songTitle").type(STRING).description("노래 제목")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 활동을 추적하여 반환합니다")
    void findFavoriteGenre() throws Exception {
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
        PlayRecord playRecord = PlayRecord.builder()
                .memberId(member.getId())
                .artistName("NewJeans")
                .songTitle("Hype Boy")
                .build();

        playRecordRepository.save(playRecord);

        // expected
        mockMvc.perform(get("/api/musics/play-record")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 활동 내역 반환",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("음악")
                                .summary("로그인한 회원의 활동 내역 반환")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("artistName").type(STRING).description("아티스트 이름"),
                                        fieldWithPath("songTitle").type(STRING).description("노래 제목"),
                                        fieldWithPath("genre").type(STRING).description("관심있는 장르")
                                )
                                .build()
                        )));
    }
}