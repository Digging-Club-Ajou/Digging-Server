package server.domain.genre;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.BaseTimeEntity;
import server.global.annotation.Association;
import server.mapper.genre.dto.GenreRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Genre extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;

    @Association
    private Long memberId;

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
    private Genre(final Long memberId, final boolean ballade, final boolean dance, final boolean rockMetal,
                 final boolean rbAndSoul, final boolean rapHiphop, final boolean folkBlues,
                 final boolean indie, final boolean pop, final boolean ostAndMusical) {
        this.memberId = memberId;
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

    public void updateGenre(final GenreRequest genreRequest) {
        this.ballade = genreRequest.ballade();
        this.dance = genreRequest.dance();
        this.rockMetal = genreRequest.rockMetal();
        this.rbAndSoul = genreRequest.rbAndSoul();
        this.rapHiphop = genreRequest.rapHiphop();
        this.folkBlues = genreRequest.folkBlues();
        this.indie = genreRequest.indie();
        this.pop = genreRequest.pop();
        this.ostAndMusical = genreRequest.ostAndMusical();
    }

    public List<String> getGenreText() {
        List<String> genreText = new ArrayList<>();
        addBallade(genreText);
        addDance(genreText);
        addRockMetal(genreText);
        addRbAndSoul(genreText);
        addRapHiphop(genreText);
        addFolkBlues(genreText);
        addIndie(genreText);
        addPop(genreText);
        addOstAndMusical(genreText);

        return genreText;
    }

    private void addOstAndMusical(final List<String> genreText) {
        if (ostAndMusical) {
            genreText.add("ostAndMusical");
        }
    }

    private void addPop(final List<String> genreText) {
        if (pop) {
            genreText.add("pop");
        }
    }

    private void addIndie(final List<String> genreText) {
        if (indie) {
            genreText.add("indie");
        }
    }

    private void addFolkBlues(final List<String> genreText) {
        if (folkBlues) {
            genreText.add("folkBlues");
        }
    }

    private void addRapHiphop(final List<String> genreText) {
        if (rapHiphop) {
            genreText.add("rapHiphop");
        }
    }

    private void addRbAndSoul(final List<String> genreText) {
        if (rbAndSoul) {
            genreText.add("rbAndSoul");
        }
    }

    private void addRockMetal(final List<String> genreText) {
        if (rockMetal) {
            genreText.add("rockMetal");
        }
    }

    private void addDance(final List<String> genreText) {
        if (dance) {
            genreText.add("dance");
        }
    }

    private void addBallade(final List<String> genreText) {
        if (ballade) {
            genreText.add("ballade");
        }
    }
}
