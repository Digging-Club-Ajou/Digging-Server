package server.domain.artist;

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
public class Era extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "era_id")
    private Long id;

    private String era;

    @Association
    private Long artistInfoId;

    @Builder
    public Era(final String era, final Long artistInfoId){
        this.era = era;
        this.artistInfoId = artistInfoId;
    }
}
