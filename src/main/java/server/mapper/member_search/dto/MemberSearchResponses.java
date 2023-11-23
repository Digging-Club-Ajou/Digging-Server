package server.mapper.member_search.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MemberSearchResponses(
        List<MemberSearchResponse> memberSearchResponses
) {
}
