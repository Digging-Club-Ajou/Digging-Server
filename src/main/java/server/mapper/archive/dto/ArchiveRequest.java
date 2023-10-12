package server.mapper.archive.dto;

import lombok.Builder;
import server.mapper.genre.dto.GenreRequest;
import server.mapper.member.dto.NicknameRequest;

import java.util.List;

@Builder
public record ArchiveRequest(
        List<String> artists,
        GenreRequest genreRequest,
        NicknameRequest nicknameRequest
) {
}
