package server.service.album;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class AlbumInfoServiceTest extends ServiceTest {

    @Autowired
    private AlbumInfoService albumInfoService;

    @DisplayName("로그인한 회원으로 앨범 정보를 저장한다.")
    @Test
    void createProfileInfo() {
        // given
        Member member = Member.builder()
                .nickname("회원 닉네임")
                .build();

        memberRepository.save(member);
        String albumName = "앨범 이름";

        // when
        albumInfoService.createProfileInfo(member.getId(), albumName);

        // then
        assertThat(albumRepository.count()).isEqualTo(1);
    }

    @DisplayName("로그인한 회원으로 앨범 정보를 수정한다.")
    @Test
    void updateProfileInfo() {
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
        Album updateAlbum = albumInfoService.updateProfileInfo(member.getId(), "수정 앨범 이름");
        String updateAlbumName = updateAlbum.getAlbumName();

        // then
        assertThat(albumRepository.count()).isEqualTo(1);
        assertThat(updateAlbumName).isEqualTo("수정 앨범 이름");
    }
}