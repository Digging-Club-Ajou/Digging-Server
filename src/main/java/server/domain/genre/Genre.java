package server.domain.genre;

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
public class Genre extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;

    @Association
    private Long memberId;

    private boolean ballade;

    private boolean dance;

    private boolean rockMetal;

    private boolean rbAndSoul;

    private boolean rapHiphop;

    private boolean folkBlues;

    private boolean indie;

    private boolean pop;

    private boolean ostAndMusical;

    @Builder
    private Genre(final Long memberId, final boolean ballade, final boolean dance, final boolean rockMetal,
                 final boolean rbAndSoul, final boolean rapHiphop, final boolean folkBlues,
                 final boolean indie, final boolean pop, final boolean ostAndMusical) {
        this.memberId = memberId;
        this.ballade = ballade;
        this.dance = dance;
        this.rockMetal = rockMetal;
        this.rbAndSoul = rbAndSoul;
        this.rapHiphop = rapHiphop;
        this.folkBlues = folkBlues;
        this.indie = indie;
        this.pop = pop;
        this.ostAndMusical = ostAndMusical;
    }
}
