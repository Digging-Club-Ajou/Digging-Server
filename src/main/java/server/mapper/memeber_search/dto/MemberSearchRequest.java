package server.mapper.memeber_search.dto;

import lombok.Builder;

@Builder
public record MemberSearchRequest (
        String keyword
){
}
