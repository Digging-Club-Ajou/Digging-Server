package server.controller.following;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.domain.member.persist.Member;
import server.mapper.following.dto.FollowingDto;
import server.mapper.following.dto.FollowingInfoDto;
import server.mapper.member.dto.NicknameRequest;
import server.util.ControllerTest;

import java.lang.reflect.Array;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.util.TestConstant.*;
public class FollowingControllerTest extends ControllerTest {
    @Test
    @DisplayName("팔로잉 정보 저장하기")
    void createFollowing() throws Exception {
        // given
        String accessToken = login();

        Long followingId = Long.parseLong("1");
        FollowingDto dto = new FollowingDto(followingId);

        // expected
        mockMvc.perform(post("/api/following")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("팔로잉 등록",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로잉")
                                .summary("팔로잉 등록")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("followingId").description("followingId")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("팔로우 삭제하기")
    void deleteFollowing() throws Exception {
        // given 1
        Member member = Member.builder()
                .nickname(TEST_NICKNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        String accessToken = login();

        Long followingId = Long.parseLong("1");
        FollowingInfoDto dto = new FollowingInfoDto(followingId);

        // expected
        mockMvc.perform(delete("/api/following")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(document("following 삭제 ",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로잉")
                                .summary("팔로잉 삭제")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("followingInfoId").description("followingInfoId")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("팔로잉 리스트 가져오")
    void getFollowingList() throws Exception {

        String accessToken = login();

        //given members
        Member member = Member.builder()
                .nickname(TEST_NICKNAME.value)
                .build();
        memberRepository.save(member);

        Member member1 = Member.builder()
                .nickname(TEST_NICKNAME1.value)
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .nickname(TEST_NICKNAME2.value)
                .build();
        memberRepository.save(member2);

        //given albums
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


        Album album3 = Album.builder()
                .memberId(member2.getId())
                .nickname(TEST_NICKNAME.value)
                .albumName("앨범 이름3")
                .build();

        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);

        //given followers
        FollowingInfo followingInfo = FollowingInfo.builder().followedId(member.getId()).followingId(member1.getId()).build();
        followingRepository.save(followingInfo);

        FollowingInfo followingInfo1 = FollowingInfo.builder().followedId(member.getId()).followingId(member2.getId()).build();
        followingRepository.save(followingInfo1);

        FollowingInfo followingInfo2 = FollowingInfo.builder().followedId(member1.getId()).followingId(member.getId()).build();
        followingRepository.save(followingInfo2);

        // expected
        mockMvc.perform(get("/api/followings")
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("팔로잉/팔로워 리스트 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로잉")
                                .summary("팔로잉/팔로워 리스트 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("followings[]").type(ARRAY).description("팔로잉 리스트"),
                                        fieldWithPath("followers[]").type(ARRAY).description("팔로워 리스트")
//                                        fieldWithPath("followings[].followingInfoId").description("회원 id"),
//                                        fieldWithPath("followings[].memberId").description("앨범 id"),
//                                        fieldWithPath("followings[].imageUrl").description("닉네임"),
//                                        fieldWithPath("followings[].nickname").description("앨범 이름"),
//                                        fieldWithPath("followings[].imageUrl").description("이미지 URL"),
//                                        fieldWithPath("followings[].isFollowForFollow").description("아티스트 이름"),
//                                        fieldWithPath("followers[].followingInfoId").description("회원 id"),
//                                        fieldWithPath("followers[].memberId").description("앨범 id"),
//                                        fieldWithPath("followers[].imageUrl").description("닉네임"),
//                                        fieldWithPath("followers[].nickname").description("앨범 이름"),
//                                        fieldWithPath("followers[].imageUrl").description("이미지 URL"),
//                                        fieldWithPath("followers[].isFollowForFollow").description("아티스트 이름")
                                )
                                .build()
                        )));
    }

}
