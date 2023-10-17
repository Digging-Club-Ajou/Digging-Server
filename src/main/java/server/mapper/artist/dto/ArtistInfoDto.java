package server.mapper.artist.dto;

import lombok.Builder;
import server.domain.artist.Era;
import server.domain.member.vo.Gender;

import java.util.List;

@Builder
public record ArtistInfoDto (
        Long id,
        String name,
        String nation,
        String gender,
        String group,

        String genre,
        int popularity

){

}
