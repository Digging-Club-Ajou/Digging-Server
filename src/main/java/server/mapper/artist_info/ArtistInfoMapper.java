package server.mapper.artist_info;

import server.domain.album.Album;
import server.domain.artist_info.ArtistInfo;
import server.domain.member.persist.Member;
import server.mapper.artist_info.dto.ArtistInfoRequest;

public class ArtistInfoMapper {

    private ArtistInfoMapper(){

    }

    public static ArtistInfo toEntity(final ArtistInfoRequest artistInfoRequest) {
        return ArtistInfo.builder()
                .artistName(artistInfoRequest.artistName())
                .ballade(artistInfoRequest.ballade())
                .dance(artistInfoRequest.dance())
                .rockMetal(artistInfoRequest.rockMetal())
                .rbAndSoul(artistInfoRequest.rbAndSoul())
                .rapHiphop(artistInfoRequest.rapHiphop())
                .folkBlues(artistInfoRequest.folkBlues())
                .indie(artistInfoRequest.indie())
                .pop(artistInfoRequest.pop())
                .ostAndMusical(artistInfoRequest.ostAndMusical())
                .build();
    }
}
