package server.dummy.dto;


public record MelodyCardDto(
        String artistName,
        String songTitle,
        String address,
        String cardDescription,
        String color,
        String melodyCardImage
) {
}
