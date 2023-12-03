package server.mapper.artist_info;

import server.domain.artist_info.ArtistInfo;
import server.mapper.artist_info.dto.ArtistInfoResponse;

public class ArtistInfoMapper {

    private ArtistInfoMapper(){

    }

    public static ArtistInfo toEntity(final ArtistInfoResponse artistInfoResponse) {
        return ArtistInfo.builder()
                .artistName(artistInfoResponse.artistName())
                .ballade(artistInfoResponse.ballade())
                .dance(artistInfoResponse.dance())
                .rockMetal(artistInfoResponse.rockMetal())
                .rbAndSoul(artistInfoResponse.rbAndSoul())
                .rapHiphop(artistInfoResponse.rapHiphop())
                .folkBlues(artistInfoResponse.folkBlues())
                .indie(artistInfoResponse.indie())
                .pop(artistInfoResponse.pop())
                .ostAndMusical(artistInfoResponse.ostAndMusical())
                .build();
    }
}
