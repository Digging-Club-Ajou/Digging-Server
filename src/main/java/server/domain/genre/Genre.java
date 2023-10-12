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

    private boolean pop;

    private boolean rapHiphop;

    private boolean folkBlues;

    private boolean indie;

    @Builder
    private Genre(final long memberId, final boolean ballade, final boolean dance, final boolean rockMetal,
                 final boolean pop, final boolean rapHiphop, final boolean folkBlues, final boolean indie) {
        this.memberId = memberId;
        this.ballade = ballade;
        this.dance = dance;
        this.rockMetal = rockMetal;
        this.pop = pop;
        this.rapHiphop = rapHiphop;
        this.folkBlues = folkBlues;
        this.indie = indie;
    }
}
