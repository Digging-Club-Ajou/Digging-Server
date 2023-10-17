package server.domain.melody_card;


import jakarta.annotation.Nullable;
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
public class MelodyCard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_melodyCard_id")
    private Long id;

    @Association
    private Long albumId;

    @Association
    private Long memberId;

    private String nickname;

    private String artistName;

    private String songTitle;

    private String genre;

    @Nullable
    private String previewUrl;

    @Nullable
    private String address;

    @Nullable
    private String cardDescription;

    private String color;

    @Builder
    private MelodyCard(final Long albumId, final Long memberId, final String nickname,
                      final String artistName, final String songTitle, final String genre,
                      final String previewUrl, final String address,
                      final String cardDescription, final String color) {
        this.albumId = albumId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.artistName = artistName;
        this.songTitle = songTitle;
        this.genre = genre;
        this.previewUrl = previewUrl;
        this.address = address;
        this.cardDescription = cardDescription;
        this.color = color;
    }
}
