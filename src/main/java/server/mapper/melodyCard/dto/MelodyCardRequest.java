package server.mapper.melodyCard.dto;

public record MelodyCardRequest (
        String singer,
        String songTitle,
        String genre,
        String streamingLink,
        String address,
        String cardDescription,
        String color
){
}