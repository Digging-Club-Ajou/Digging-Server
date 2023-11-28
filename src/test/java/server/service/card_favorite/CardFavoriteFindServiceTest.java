package server.service.card_favorite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.album.Album;
import server.domain.card_favorite.CardFavorite;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.mapper.card_favorite.dto.CardFavoriteResponse;
import server.util.ServiceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CardFavoriteFindServiceTest extends ServiceTest {

    @Autowired
    private CardFavoriteFindService cardFavoriteFindService;

    @DisplayName("특정 회원의 좋아요 정보를 최신순으로 모두 가져온다.")
    @Test
    void findAllResponses() {
        // given 1
        Member member = Member.builder()
                .nickname("회원 닉네임")
                .build();

        memberRepository.save(member);

        Album album = Album.builder()
                .memberId(member.getId())
                .build();

        albumRepository.save(album);

        // given 2

        MelodyCard melodyCard1 = MelodyCard.builder()
                .memberId(member.getId())
                .albumId(album.getId())
                .artistName("artist 1")
                .build();

        MelodyCard melodyCard2 = MelodyCard.builder()
                .memberId(member.getId())
                .albumId(album.getId())
                .artistName("artist 2")
                .build();

        melodyCardRepository.save(melodyCard1);
        melodyCardRepository.save(melodyCard2);

        // given 3
        CardFavorite cardFavorite1 = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard1.getId())
                .build();

        CardFavorite cardFavorite2 = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard2.getId())
                .build();

        cardFavoriteRepository.save(cardFavorite1);
        cardFavoriteRepository.save(cardFavorite2);

        // when
        List<CardFavoriteResponse> cardFavoriteResponses = cardFavoriteFindService.findAllResponses(member.getId());

        // then
        assertThat(cardFavoriteResponses.size()).isEqualTo(2);
        assertThat(cardFavoriteResponses.get(0).artistName()).isEqualTo("artist 2");
        assertThat(cardFavoriteResponses.get(1).artistName()).isEqualTo("artist 1");
    }
}