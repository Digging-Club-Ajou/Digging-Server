package server.mapper.memeber_search.dto;

import lombok.Builder;

@Builder
public record MemberSearchResponse(
        String imageUrl,
        String nickname
) {
}
