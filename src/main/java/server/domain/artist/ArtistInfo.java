package server.domain.artist;

import jakarta.persistence.*;
import lombok.*;
import server.domain.BaseTimeEntity;
import server.domain.member.vo.Gender;
import server.global.annotation.Association;

@Getter
@Setter
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
    private String isGroup;

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
        this.isGroup = group;
        this.genreId = genreId;
        this.popularity = popularity;
    }
}
