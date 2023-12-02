package server.mapper.artist_info.dto;

public record ArtistInfoRequest(
        String artistName,
        boolean ballade,
        boolean dance,
        boolean rockMetal,
        boolean rbAndSoul,
        boolean rapHiphop,
        boolean folkBlues,
        boolean indie,
        boolean pop,
        boolean ostAndMusical
) {
}
