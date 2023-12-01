package server.service.card_favorite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.card_favorite.CardFavorite;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;


class CardFavoriteCreateServiceTest extends ServiceTest {

    @Autowired
    private CardFavoriteCreateService cardFavoriteCreateService;

    @DisplayName("특정 회원이 멜로디 카드에 좋아요를 누른다.")
    @Test
    void saveFavorite() {
        // given
        Member member = Member.builder()
                .nickname("회원 닉네임")
                .build();

        memberRepository.save(member);

        MelodyCard melodyCard = MelodyCard.builder()
                .memberId(9999L)
                .build();

        melodyCardRepository.save(melodyCard);

        // when
        cardFavoriteCreateService.saveFavorite(member.getId(), melodyCard.getId());

        // then
        assertThat(cardFavoriteRepository.count()).isEqualTo(1);
    }

    @DisplayName("특정 회원의 멜로디 카드에 대한 좋아요 정보가 이미 존재한다면 업데이트한다.")
    @Test
    void updateFavorite() {
        // given 1
        Member member = Member.builder()
                .nickname("회원 닉네임")
                .build();

        memberRepository.save(member);

        MelodyCard melodyCard = MelodyCard.builder()
                .memberId(9999L)
                .build();

        melodyCardRepository.save(melodyCard);

        // given 2
        CardFavorite cardFavorite = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard.getId())
                .build();

        cardFavoriteRepository.save(cardFavorite);

        // when
        cardFavoriteCreateService.saveFavorite(member.getId(), melodyCard.getId());

        // then
        assertThat(cardFavoriteRepository.count()).isEqualTo(1);
    }
}