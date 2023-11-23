package server.mapper.member_search.dto;

import lombok.Builder;

@Builder
public record MemberSearchResponse(
        String nickname,
        long albumId
) {
}
