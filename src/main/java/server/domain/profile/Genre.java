package server.domain.profile;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import server.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_genre_id")
    private Long id;

    @Association
    private Long memberId;

    private int kPop;

    private int jPop;

    private int rock;

    private int pop;

    private DateTime createAt;

    private DateTime lastModifiedAt;


    @Builder
    private Genre(final Long memberId, final int kPop, final int jPop, final int rock, final int pop,
                  final DateTime createAt, final DateTime lastModifiedAt) {

        this.memberId = memberId;
        this.kPop = kPop;
        this.jPop = jPop;
        this.rock = rock;
        this.pop = pop;
        this.createAt = createAt;
        this.lastModifiedAt = lastModifiedAt;

    }

}
