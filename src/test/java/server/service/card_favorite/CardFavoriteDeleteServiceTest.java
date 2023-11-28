package server.service.card_favorite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.card_favorite.CardFavorite;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.util.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class CardFavoriteDeleteServiceTest extends ServiceTest {

    @Autowired
    protected CardFavoriteDeleteService cardFavoriteDeleteService;

    @DisplayName("특정 회원의 멜로디 카드에 대한 좋아요 정보가 이미 존재한다면 삭제한다.")
    @Test
    void deleteFavorite() {
        // given 1
        Member member = Member.builder()
                .nickname("회원 닉네임")
                .build();

        memberRepository.save(member);

        MelodyCard melodyCard = MelodyCard.builder()
                .memberId(member.getId())
                .build();

        melodyCardRepository.save(melodyCard);

        // given 2
        CardFavorite cardFavorite = CardFavorite.builder()
                .memberId(member.getId())
                .melodyCardId(melodyCard.getId())
                .build();

        cardFavoriteRepository.save(cardFavorite);

        // when
        cardFavoriteDeleteService.deleteFavorite(member.getId(), melodyCard.getMemberId());

        // then
        assertThat(cardFavoriteRepository.count()).isEqualTo(0);
    }
}