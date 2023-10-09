package server.controller.album;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.mapper.album.dto.AlbumNameRequest;
import server.util.ControllerTest;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;


class AlbumControllerTest extends ControllerTest {

    @Test
    @DisplayName("형식에 맞지 않는 앨범 이름이면 예외가 발생합니다")
    void validateAlbumNameEmoticonFail() throws Exception {
        // given
        String accessToken = login();
        AlbumNameRequest dto = new AlbumNameRequest("특수문자, 이모티콘 사용@#$%");

        // expected
        mockMvc.perform(post("/api/albums/name-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("앨범 이름에 특수문자, 이모티콘 사용",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 이름 검증")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("형식에 맞지 않는 앨범 이름이면 예외가 발생합니다")
    void validateAlbumName() throws Exception {
        // given
        String accessToken = login();
        AlbumNameRequest dto = new AlbumNameRequest("글자 수가 15글자가 넘는 앨범 이름은 생성할 수 없음");

        // expected
        mockMvc.perform(post("/api/albums/name-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("앨범 이름 15글자 넘음",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 이름 검증")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 앨범이 존재하지 않으면 false를 반환합니다")
    void validateAlbumExistFalse() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(post("/api/albums-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 앨범이 존재하지 않음",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 존재 확인 (앨범 존재 여부 판단)")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("alreadyExist").type(BOOLEAN).description("앨범 존재")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 앨범이 존재하지 않으면 true를 반환합니다")
    void validateAlbumExistTrue() throws Exception {
        // given
        String accessToken = loginAndCreateAlbum();

        // expected
        mockMvc.perform(post("/api/albums-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("로그인한 회원의 앨범이 존재함",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 존재 확인 (앨범 존재 여부 판단)")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("alreadyExist").type(BOOLEAN).description("앨범 존재")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 앨범이 존재하지 않으면 앨범 이미지를 가져올 수 없습니다")
    void getAlbumImageUrlFail() throws Exception {
        // given
        String accessToken = login();

        // expected
        mockMvc.perform(get("/api/albums-image")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isNotFound())
                .andDo(document("앨범 이미지 URL 가져오기 - 실패",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 이미지 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("statusCode").type(STRING).description("상태 코드"),
                                        fieldWithPath("message").type(STRING).description("오류 메세지")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 앨범이 존재하면 앨범 이미지 url을 가져옵니다")
    void getAlbumImageUrlSuccess() throws Exception {
        // given
        String accessToken = loginAndCreateAlbum();

        // expected
        mockMvc.perform(get("/api/albums-image")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("앨범 이미지 URL 가져오기 - 성공",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("앨범")
                                .summary("앨범 이미지 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("imageUrl").type(STRING).description("앨범 이미지 url")
                                )
                                .build()
                        )));
    }
}