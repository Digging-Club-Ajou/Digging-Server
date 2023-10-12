package server.domain.genre;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import server.domain.BaseTimeEntity;
import server.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Genre extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_genre_id")
    private Long id;

    @Association
    private Long memberId;

    private boolean kPop;

    private boolean jPop;

    private boolean rock;

    private boolean pop;

    @Builder
    private Genre(final Long memberId, final boolean kPop, final boolean jPop, final boolean rock, final boolean pop) {

        this.memberId = memberId;
        this.kPop = kPop;
        this.jPop = jPop;
        this.rock = rock;
        this.pop = pop;
    }

}
