package server.mapper.melody_card.dto;

import jakarta.annotation.Nullable;

public record MelodyCardRequest (
        String artistName,
        String songTitle,
        String genre,
        String previewUrl,
        @Nullable
        String address,
        @Nullable
        String cardDescription,
        String color,
        String albumCoverImageUrl
){
}