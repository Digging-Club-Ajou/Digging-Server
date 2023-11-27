package server.service.album;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class AlbumValidationServiceTest extends ServiceTest {

    @Autowired
    private AlbumValidationService albumValidationService;

    @DisplayName("회원의 앨범이 존재하면 true를 반환한다.")
    @Test
    void validateExistByMemberIdTrue() {
        // given
        Member member = Member.builder()
                .nickname("회원 닉네임")
                .build();

        memberRepository.save(member);

        Album album = Album.builder()
                .memberId(member.getId())
                .albumName("앨범 이름")
                .build();

        albumRepository.save(album);

        // when
        boolean isExist = albumValidationService.validateExistByMemberId(member.getId());

        // then
        assertThat(isExist).isTrue();
    }

    @DisplayName("회원의 앨범이 존재하지 않으면 false를 반환한다.")
    @Test
    void validateExistByMemberIdFail() {
        // when
        boolean isExist = albumValidationService.validateExistByMemberId(9999L);

        // then
        assertThat(isExist).isFalse();
    }

    @DisplayName("앨범 id와 일치하는 앨범이 존재하면 true를 반환한다.")
    @Test
    void validateExistByAlbumIdTrue() {
        // given
        Album album = Album.builder()
                .albumName("앨범 이름")
                .build();

        albumRepository.save(album);

        // when
        boolean isExist = albumValidationService.validateExistByAlbumId(album.getId());

        // then
        assertThat(isExist).isTrue();
    }

    @DisplayName("앨범 id와 일치하는 앨범이 존재하지 않으면 false를 반환한다.")
    @Test
    void validateExistByAlbumIdFalse() {
        // when
        boolean isExist = albumValidationService.validateExistByAlbumId(9999L);

        // then
        assertThat(isExist).isFalse();
    }
}