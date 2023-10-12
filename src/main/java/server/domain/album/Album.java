package server.domain.album;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @Association
    private Long memberId;

    private String nickname;

    private String albumName;

    @Builder
    public Album(final Long memberId, final String nickname, final String albumName) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.albumName = albumName;
    }
}
