package server.mapper.following.dto;

import lombok.Builder;

@Builder
public record FollowingResponse(
        Long memberId,
        Long albumId,
        String nickname,
        Boolean isFollowing,
        Boolean isFollower

) {

}
