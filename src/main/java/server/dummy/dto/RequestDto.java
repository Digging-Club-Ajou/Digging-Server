package server.dummy.dto;

import java.util.List;

public record RequestDto(
        AlbumDto albumDto,
        List<MelodyCardDto> melodyCardDtos
) {
}
