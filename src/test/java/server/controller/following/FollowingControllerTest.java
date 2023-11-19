package server.controller.following;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.following.dto.FollowingDto;
import server.mapper.following.dto.FollowingInfoDto;
import server.mapper.member.dto.NicknameRequest;
import server.util.ControllerTest;


import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.util.TestConstant.*;
public class FollowingControllerTest extends ControllerTest {
    @Test
    @DisplayName("팔로잉 정보 저장하기")
    void createFollowing() throws Exception {
        // given
        String accessToken = login();

        Long followingId = Long.parseLong("2");
        FollowingDto dto = new FollowingDto(followingId);

        // expected
        mockMvc.perform(post("/api/following")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNoContent())
                .andDo(document("팔로잉 등록",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로잉")
                                .summary("팔로잉 등록")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("memberId").description("팔로잉 하려는 memberId")
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

        //given 3

        Long followedId = Long.parseLong("1");
        FollowingInfoDto dto = new FollowingInfoDto(member.getId(),followedId);
        FollowingInfo followingInfo = FollowingInfo.builder().followingId(member.getId()).followedId(followedId).build();
        followingRepository.save(followingInfo);

        // expected
        mockMvc.perform(delete("/api/following")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNoContent())
                .andDo(document("following 삭제 ",
                        preprocessRequest(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로잉")
                                .summary("팔로잉 삭제")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .requestFields(
                                        fieldWithPath("followingId").description("memberId"),
                                        fieldWithPath("followedId").description("팔로잉 삭제 하려는 memberId")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("특정 회원의 팔로잉/팔로우 리스트 가져오기")
    void getFollowings() throws Exception {
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

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
        mockMvc.perform(get("/api/followings/{memberId}", member.getId())
                        .header(ACCESS_TOKEN.value, accessToken)
                )
                .andExpect(status().isOk())
                .andDo(document("특정 회원의 팔로잉/팔로우 리스트 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로잉")
                                .summary("특정 회원의 팔로잉/팔로워 리스트 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )

                                .responseFields(
                                        fieldWithPath("followings[].memberId").description("멤버 id"),
                                        fieldWithPath("followings[].albumId").description("앨범 id"),
                                        fieldWithPath("followings[].nickname").description("멤버 닉네임"),
                                        fieldWithPath("followings[].isFollowing").description("로그인 한 회원의 팔로잉 여부"),
                                        fieldWithPath("followings[].isFollower").description("로그인 한 회원의 팔로워 여부"),
                                        fieldWithPath("followers[].memberId").description("멤버 id"),
                                        fieldWithPath("followers[].albumId").description("앨범 id"),
                                        fieldWithPath("followers[].nickname").description("멤버 닉네임"),
                                        fieldWithPath("followers[].isFollowing").description("로그인 한 회원의 팔로잉 여부"),
                                        fieldWithPath("followers[].isFollower").description("로그인 한 회원의 팔로워 여부")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("로그인한 회원의 팔로잉/팔로우 리스트 가져오기")
    void getMembersFollowings() throws Exception {
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);

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
                .andDo(document("로그인한 회원의 팔로잉/팔로우 리스트 가져오기",
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로잉")
                                .summary("로그인한 회원의 팔로잉/팔로워 리스트 가져오기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .responseFields(
                                        fieldWithPath("followings[].memberId").description("멤버 id"),
                                        fieldWithPath("followings[].albumId").description("앨범 id"),
                                        fieldWithPath("followings[].nickname").description("멤버 닉네임"),
                                        fieldWithPath("followings[].isFollowing").description("로그인 한 회원의 팔로잉 여부"),
                                        fieldWithPath("followings[].isFollower").description("로그인 한 회원의 팔로워 여부"),
                                        fieldWithPath("followers[].memberId").description("멤버 id"),
                                        fieldWithPath("followers[].albumId").description("앨범 id"),
                                        fieldWithPath("followers[].nickname").description("멤버 닉네임"),
                                        fieldWithPath("followers[].isFollowing").description("로그인 한 회원의 팔로잉 여부"),
                                        fieldWithPath("followers[].isFollower").description("로그인 한 회원의 팔로워 여부")
                                )
                                .build()
                        )));
    }

    @Test
    @DisplayName("팔로우가 되어있으면 True, 아니면 False를 반환합니다.")
    void getFollowing() throws Exception {
        // given 1
        Member member = Member.builder()
                .nickname(TEST_NICKNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        String accessToken = login();


        // expected
        mockMvc.perform(get("/api/following-validation")
                        .header(ACCESS_TOKEN.value, accessToken)
                        .param("memberId", member.getId().toString())
                )
                .andExpect(status().isOk())
                .andDo(document("팔로잉 여부 확인하기 ",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("팔로잉")
                                .summary("팔로잉 여부 확인하기")
                                .requestHeaders(
                                        headerWithName(ACCESS_TOKEN.value).description("AccessToken")
                                )
                                .queryParameters(
                                        parameterWithName("memberId")
                                                .description("팔로잉 여부 확인할 memberId")
                                )
                                .responseFields(
                                        fieldWithPath("isFollowing").type(BOOLEAN).description("팔로잉 여부")
                                )
                                .build()
                        )));
    }
}
