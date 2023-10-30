package server.mapper.following.dto;

import lombok.Builder;

@Builder
public record FollowingResponse(
        Long followingInfoId,
        Long memberId,
        String imageUrl,
        String nickname,
        Boolean isFollowForFollow

) {
}
