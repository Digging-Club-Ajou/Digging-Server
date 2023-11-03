package server.controller.notification;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.domain.notification.persist.NotificationInfo;
import server.mapper.notification.NotificationInfoMapper;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.util.TestConstant.TEST_USERNAME;

class NotificationControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인한 회원의 알림 목록을 가져옵니다")
    void getNotifications() throws Exception {
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
        NotificationInfo notificationInfo1 =
                NotificationInfoMapper.toEntity(member.getId(), "알림 메세지1");

        NotificationInfo notificationInfo2 =
                NotificationInfoMapper.toEntity(member.getId(), "알림 메세지2");

        NotificationInfo notificationInfo3 =
                NotificationInfoMapper.toEntity(member.getId(), "알림 메세지3");

        notificationRepository.save(notificationInfo1);
        notificationRepository.save(notificationInfo2);
        notificationRepository.save(notificationInfo3);

        // expected
        mockMvc.perform(get("/api/notifications")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 알림 목록 가져오기 (최신순)",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("알림")
                                .summary("알림 목록 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("notificationResponses[].notificationId").description("알림 메시지 id"),
                                        fieldWithPath("notificationResponses[].message").description("알림 메시지"),
                                        fieldWithPath("notificationResponses[].minutes").description("알림 시간 (분)")
                                )
                                .build()
                        )));
    }
}