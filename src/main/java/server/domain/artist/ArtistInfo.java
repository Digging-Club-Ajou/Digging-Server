package server.domain.artist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.BaseTimeEntity;
import server.domain.genre.Genre;
import server.domain.member.vo.Gender;
import server.global.annotation.Association;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ArtistInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_info_id")
    private Long id;
    private String name;
    private String imageURL;
    private String nation;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String group;

    @Association
    private Long genreId;
    private int popularity;

    @Builder
    public ArtistInfo(final String name, final String imageURL, final String nation, final Gender gender,
                      final String group,  final Long genreId, final int popularity){
        this.name = name;
        this.imageURL = imageURL;
        this.nation = nation;
        this.gender = gender;
        this.group = group;
        this.genreId = genreId;
        this.popularity = popularity;
    }
}
