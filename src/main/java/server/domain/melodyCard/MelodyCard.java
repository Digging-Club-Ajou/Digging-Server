package server.domain.melodyCard;


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

    private String singer;

    private String songTitle;

    private String genre;

    private String streamingLink;

    private String address;

    private String cardDescription;

    private String color;

    @Builder
    private MelodyCard(final Long albumId, final String singer, final String songTitle,
                       final String genre, final String streamingLink, final String address, final String cardDescription, final String color) {
        this.albumId = albumId;
        this.singer = singer;
        this.songTitle = songTitle;
        this.genre = genre;
        this.streamingLink = streamingLink;
        this.address = address;
        this.cardDescription = cardDescription;
        this.color = color;

    }

}
