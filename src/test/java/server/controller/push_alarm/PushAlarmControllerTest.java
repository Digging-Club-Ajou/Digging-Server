package server.controller.push_alarm;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.push_alarm.dto.PushAlarmInfo;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.util.TestConstant.TEST_USERNAME;

class PushAlarmControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인한 회원의 푸쉬 알림 여부를 가져옵니다")
    void getPushAlarmInfo() throws Exception {
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

        // expected
        mockMvc.perform(get("/api/push-alarm")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("푸쉬 알림 여부 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("알림")
                                .summary("푸쉬 알림 여부 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("pushAlarm").type(BOOLEAN).description("푸쉬 알림 여부")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 푸쉬 알림 여부를 수정합니다.")
    void updatePushAlarmInfo() throws Exception {
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
        PushAlarmInfo pushAlarmInfo = new PushAlarmInfo(true);

        // expected
        mockMvc.perform(post("/api/push-alarm")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pushAlarmInfo))
                )
                .andExpect(status().isNoContent())
                .andDo(document("푸쉬 알림 여부 설정하기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("알림")
                                .summary("푸쉬 알림 여부 설정하기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("pushAlarm").type(BOOLEAN).description("푸쉬 알림 여부")
                                )
                                .build()
                        )));
    }
}