package server.mapper.member_search.dto;

import lombok.Builder;

@Builder
public record MemberSearchRequest (
        String keyword
){
}
