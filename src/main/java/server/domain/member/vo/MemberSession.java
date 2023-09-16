package server.domain.member.vo;

import lombok.Builder;

@Builder
public record MemberSession(
        long id,
        String username,
        String nickname
) {
}
