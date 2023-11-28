package server.service.following;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.domain.member.persist.Member;
import server.mapper.album.dto.AlbumResponse;
import server.util.ServiceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class FollowingAlbumFindServiceTest extends ServiceTest {

    @Autowired
    private FollowingAlbumFindService followingAlbumFindService;

    @DisplayName("팔로잉한 회원의 앨범 정보들을 가져온다.")
    @Test
    void findAll() {
        // given
        Member member1 = Member.builder()
                .nickname("회원 닉네임 1")
                .build();

        Member member2 = Member.builder()
                .nickname("회원 닉네임 2")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        Album album = Album.builder()
                .memberId(member1.getId())
                .build();

        albumRepository.save(album);

        FollowingInfo followingInfo = FollowingInfo.builder()
                .followedId(member1.getId())
                .followingId(member2.getId())
                .build();

        followingRepository.save(followingInfo);

        // when
        List<AlbumResponse> albumResponses = followingAlbumFindService.findAll(member2.getId());
        AlbumResponse albumResponse = albumResponses.get(0);

        // then
        assertThat(albumResponses.size()).isEqualTo(1);
        assertThat(albumResponse.artistNames().size()).isEqualTo(3);
    }
}