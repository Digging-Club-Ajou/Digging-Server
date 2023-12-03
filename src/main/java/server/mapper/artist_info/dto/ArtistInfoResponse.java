package server.mapper.artist_info.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistInfoResponse(
        @JsonProperty("artistName")
        String artistName,
        @JsonProperty("ballade")
        boolean ballade,
        @JsonProperty("dance")
        boolean dance,
        @JsonProperty("rockMetal")
        boolean rockMetal,
        @JsonProperty("rbAndSoul")
        boolean rbAndSoul,
        @JsonProperty("rapHiphop")
        boolean rapHiphop,
        @JsonProperty("folkBlues")
        boolean folkBlues,
        @JsonProperty("indie")
        boolean indie,
        @JsonProperty("pop")
        boolean pop,
        @JsonProperty("ostAndMusical")
        boolean ostAndMusical
) {
}
