package server.domain.music_recommentdation;

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
public class MusicRecommendation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "music_recommendation_id")
    private Long id;

    @Association
    private Long memberId;

    private String artistName;

    private String songTitle;

    @Builder
    private MusicRecommendation(final long memberId, final String artistName, final String songTitle) {
        this.memberId = memberId;
        this.artistName = artistName;
        this.songTitle = songTitle;
    }
}
