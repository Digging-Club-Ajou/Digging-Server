package server.domain.artist_info;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.BaseTimeEntity;
import server.mapper.artist_info.dto.ArtistInfoRequest;
import server.mapper.genre.dto.GenreRequest;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ArtistInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_genre_id")
    private Long id;

    private String artistName;

    private boolean ballade;

    private boolean dance;

    private boolean rockMetal;

    private boolean rbAndSoul;

    private boolean rapHiphop;

    private boolean folkBlues;

    private boolean indie;

    private boolean pop;

    private boolean ostAndMusical;

    @Builder
    private ArtistInfo(final String artistName, final boolean ballade, final boolean dance, final boolean rockMetal,
                       final boolean rbAndSoul, final boolean rapHiphop, final boolean folkBlues,
                       final boolean indie, final boolean pop, final boolean ostAndMusical) {
        this.artistName = artistName;
        this.ballade = ballade;
        this.dance = dance;
        this.rockMetal = rockMetal;
        this.rbAndSoul = rbAndSoul;
        this.rapHiphop = rapHiphop;
        this.folkBlues = folkBlues;
        this.indie = indie;
        this.pop = pop;
        this.ostAndMusical = ostAndMusical;
    }

    public void updateArtistInfo(final ArtistInfoRequest artistInfoRequest) {
        this.ballade = artistInfoRequest.ballade();
        this.dance = artistInfoRequest.dance();
        this.rockMetal = artistInfoRequest.rockMetal();
        this.rbAndSoul = artistInfoRequest.rbAndSoul();
        this.rapHiphop = artistInfoRequest.rapHiphop();
        this.folkBlues = artistInfoRequest.folkBlues();
        this.indie = artistInfoRequest.indie();
        this.pop = artistInfoRequest.pop();
        this.ostAndMusical = artistInfoRequest.ostAndMusical();
    }


}
