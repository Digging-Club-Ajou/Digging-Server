package server.domain.profile;

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
public class Album extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @Association
    private Long memberId;

    private String albumName;

    private String albumDescription;

    @Builder
    private Album(final Long memberId, final String albumName, final String albumDescription) {
        this.memberId = memberId;
        this.albumName = albumName;
        this.albumDescription = albumDescription;
    }
}
