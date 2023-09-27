package server.controller.oauth;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import server.mapper.member.dto.AuthCodeRequest;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.util.TestConstant.*;

class KakaoControllerTest extends ControllerTest {

    @Test
    @DisplayName("인가코드를 받고 카카오 로그인에 성공합니다")
    void kakaoLoginSuccess() throws Exception {
        // given
        AuthCodeRequest dto = new AuthCodeRequest(TEST_AUTH_CODE.value);

        // expected
        mockMvc.perform(post("/api/kakao")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("카카오 로그인 성공",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원")
                                .summary("카카오 로그인")
                                .requestFields(
                                        fieldWithPath("authCode").type(STRING).description("인가 코드")
                                )
                                .responseHeaders(
                                        headerWithName("AccessToken").description("JWT Access Token")
                                )
                                .build()
                        )));
    }
}