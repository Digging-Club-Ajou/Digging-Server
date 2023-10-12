package server.mapper.genre.dto;

import lombok.Builder;

@Builder
public record GenreRequest(
        boolean ballade,
        boolean dance,
        boolean rockMetal,
        boolean pop,
        boolean rapHiphop,
        boolean folkBlues,
        boolean indie
){
}
