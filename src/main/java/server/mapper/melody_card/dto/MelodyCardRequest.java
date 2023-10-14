package server.mapper.melody_card.dto;

public record MelodyCardRequest (
        String artistName,
        String songTitle,
        String genre,
        String previewUrl,
        String address,
        String cardDescription,
        String color
){
}