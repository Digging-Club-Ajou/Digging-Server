package server.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.domain.album.Album;
import server.repository.album.AlbumRepository;
import server.repository.card_favorite.CardFavoriteRepository;
import server.repository.following.FollowingRepository;
import server.repository.melody_card.MelodyCardRepository;
import server.repository.member.MemberRepository;
import server.repository.notification_info.NotificationRepository;
import server.service.album.AlbumFindService;
import server.service.jwt.JwtFacade;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static server.global.constant.TextConstant.ACCESS_TOKEN;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.global.constant.TimeConstant.ONE_MONTH;
import static server.util.TestConstant.*;

@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@Profile("test")
@AcceptanceTest
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected AlbumRepository albumRepository;

    @Autowired
    protected NotificationRepository notificationRepository;

    @Autowired
    protected MelodyCardRepository melodyCardRepository;

    @Autowired
    protected CardFavoriteRepository cardFavoriteRepository;

    @Autowired
    protected FollowingRepository followingRepository;

    @Autowired
    protected JwtFacade jwtFacade;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                        .and()
                        .uris()
                        .withScheme("http")
                        .withHost("3.34.171.76")
                        .withPort(8080))
                .build();
    }

    protected String login() {
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

        MockHttpServletResponse response = new MockHttpServletResponse();

        // given 3
        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);
        String refreshToken = jwtFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
        jwtFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
        jwtFacade.setHeader(response, accessToken);

        return response.getHeader(ACCESS_TOKEN.value);
    }

    protected String loginCreateName() {
        // given 1
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .nickname(TEST_NICKNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .nickname(TEST_NICKNAME.value)
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();

        // given 3
        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);
        String refreshToken = jwtFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
        jwtFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
        jwtFacade.setHeader(response, accessToken);

        return response.getHeader(ACCESS_TOKEN.value);
    }

    protected String loginAndCreateAlbum() {
        // given 1
        Member member = Member.builder()
                .username(TEST_USERNAME.value)
                .build();

        memberRepository.save(member);

        // given 2
        Album album = Album.builder()
                .memberId(member.getId())
                .build();

        albumRepository.save(album);

        // given 3
        MemberSession memberSession = MemberSession.builder()
                .id(member.getId())
                .username(TEST_USERNAME.value)
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();

        // given 3
        String accessToken = jwtFacade.createAccessToken(memberSession, ONE_HOUR.value);
        String refreshToken = jwtFacade.createRefreshToken(memberSession.id(), ONE_MONTH.value);
        jwtFacade.saveJwtRefreshToken(memberSession.id(), refreshToken);
        jwtFacade.setHeader(response, accessToken);

        return response.getHeader(ACCESS_TOKEN.value);
    }
}
