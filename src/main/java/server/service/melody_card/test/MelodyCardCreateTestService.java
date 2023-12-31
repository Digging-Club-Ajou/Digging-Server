package server.service.melody_card.test;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.domain.member.vo.MemberSession;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.service.melody_card.MelodyCardCreateService;

import java.util.ArrayList;
import java.util.List;

// Test Double
@Profile("test")
@Service
public class MelodyCardCreateTestService implements MelodyCardCreateService {

    // API 문서화 용도
    @Override
    public void createMelodyCard(final long memberId, final MelodyCardRequest dto, final MultipartFile melodyCardImage) {

    }

    @Override
    public MelodyCardResponse getMelodyCard(final long memberId, final long melodyCardId) {
        return MelodyCardResponse.builder()
                .melodyCardId(1L)
                .albumId(1L)
                .memberId(1L)
                .nickname("testnickname123")
                .artistName("NewJeans")
                .songTitle("Super Shy")
                .imageUrl("https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af")
                .previewUrl("https://p.scdn.co/mp3-preview/dab062e2cc708a2680ce84953a3581c5a679a230?" +
                        "cid=eb9c2bc88c014e5e9a78cdb743d7a6df")
                .albumCoverImageUrl("http://test-album-cover-image-url")
                .address("도로명 주소")
                .cardDescription("카드에 대한 설명")
                .color("#FFFFFF")
                .isImageUrl(true)
                .isLike(true)
                .build();
    }

    public List<MelodyCardResponse> getMelodyCards(final long memberId, final long albumId) {
        MelodyCardResponse melodyCardResponse1 = MelodyCardResponse.builder()
                .melodyCardId(1L)
                .albumId(1L)
                .memberId(1L)
                .nickname("testnickname123")
                .artistName("NewJeans")
                .songTitle("Super Shy")
                .imageUrl("https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af")
                .previewUrl("https://p.scdn.co/mp3-preview/dab062e2cc708a2680ce84953a3581c5a679a230?" +
        "cid=eb9c2bc88c014e5e9a78cdb743d7a6df")
                .albumCoverImageUrl("http://test-album-cover-image-url")
                .address("도로명 주소")
                .cardDescription("카드에 대한 설명1")
                .color("#FFFFFF")
                .isImageUrl(true)
                .isLike(true)
                .build();

        MelodyCardResponse melodyCardResponse2 = MelodyCardResponse.builder()
                .melodyCardId(2L)
                .albumId(1L)
                .memberId(1L)
                .nickname("testnickname123")
                .artistName("NewJeans")
                .songTitle("Super Shy2")
                .imageUrl("https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af")
                .previewUrl("https://p.scdn.co/mp3-preview/dab062e2cc708a2680ce84953a3581c5a679a230?" +
                        "cid=eb9c2bc88c014e5e9a78cdb743d7a6df")
                .albumCoverImageUrl("http://test-album-cover-image-url")
                .address("도로명 주소")
                .cardDescription("카드에 대한 설명2")
                .color("#FFFFFF")
                .isImageUrl(true)
                .isLike(true)
                .build();

        MelodyCardResponse melodyCardResponse3 = MelodyCardResponse.builder()
                .melodyCardId(3L)
                .albumId(1L)
                .memberId(1L)
                .nickname("testnickname123")
                .artistName("NewJeans")
                .songTitle("Super Shy3")
                .imageUrl("https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af")
                .previewUrl("https://p.scdn.co/mp3-preview/dab062e2cc708a2680ce84953a3581c5a679a230?" +
                        "cid=eb9c2bc88c014e5e9a78cdb743d7a6df")
                .albumCoverImageUrl("http://test-album-cover-image-url")
                .address("도로명 주소")
                .cardDescription("카드에 대한 설명3")
                .color("#FFFFFF")
                .isImageUrl(true)
                .isLike(true)
                .build();

        List<MelodyCardResponse> melodyCardResponses = new ArrayList<>();
        melodyCardResponses.add(melodyCardResponse1);
        melodyCardResponses.add(melodyCardResponse2);
        melodyCardResponses.add(melodyCardResponse3);

        return melodyCardResponses;
    }

}
