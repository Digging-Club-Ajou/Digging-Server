package server.domain.card_favorite;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.BaseTimeEntity;
import server.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CardFavorite extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_favorite_id")
    private Long id;

    @Association
    private Long memberId;

    @Association
    private Long melodyCardId;

    @Builder
    private CardFavorite(final long memberId, final long melodyCardId) {
        this.memberId = memberId;
        this.melodyCardId = melodyCardId;
    }
}
