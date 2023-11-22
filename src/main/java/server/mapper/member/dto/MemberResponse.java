package server.mapper.member.dto;

import lombok.Builder;
import server.domain.member.vo.Gender;

@Builder
public record MemberResponse(
        long memberId,
        long albumId,
        String nickname,
        Gender gender,
        String phoneNumber,
        String email
) {
}
