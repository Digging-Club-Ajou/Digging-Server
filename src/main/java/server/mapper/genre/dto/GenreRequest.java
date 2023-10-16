package server.mapper.genre.dto;

import lombok.Builder;

@Builder
public record GenreRequest(
        boolean ballade,
        boolean dance,
        boolean rockMetal,
        boolean rbAndSoul,
        boolean rapHiphop,
        boolean folkBlues,
        boolean indie,
        boolean pop,
        boolean ostAndMusical
){
}
