package server.dummy.dto;

import java.util.List;

public record RequestDto(
        MemberDto memberDto,
        AlbumDto albumDto,
        List<MelodyCardDto> melodyCardDtos
) {
}
