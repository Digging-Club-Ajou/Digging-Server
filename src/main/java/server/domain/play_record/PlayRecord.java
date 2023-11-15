package server.domain.play_record;

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
public class PlayRecord extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "play_record_id")
    private Long id;

    @Association
    private Long memberId;

    private String artistName;

    private String songTitle;

    @Builder
    private PlayRecord(final long memberId, final String artistName, final String songTitle) {
        this.memberId = memberId;
        this.artistName = artistName;
        this.songTitle = songTitle;
    }
}
