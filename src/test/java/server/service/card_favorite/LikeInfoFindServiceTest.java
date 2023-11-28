package server.service.card_favorite;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.card_favorite.CardFavorite;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.util.ServiceTest;

import static org.assertj.core.api.Assertions.*;

class LikeInfoFindServiceTest extends ServiceTest {

    @Autowired
    private LikeInfoFindService likeInfoFindService;

    @DisplayName("특정 회원이 특정 카드에 좋아요 여부를 가져온다.")
    @Test
    void findLikeInfo() {
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
        Boolean likeInfoTrue = likeInfoFindService.findLikeInfo(member.getId(), melodyCard.getId());
        Boolean likeInfoFalse = likeInfoFindService.findLikeInfo(9999L, 9999L);

        // then
        assertThat(likeInfoTrue).isTrue();
        assertThat(likeInfoFalse).isFalse();
    }
}