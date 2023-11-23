package server.mapper.following.dto;

import lombok.Builder;

@Builder
public record FollowingResponse(
        long memberId,
        long albumId,
        String nickname,
        Boolean isFollowing,
        Boolean isFollower

) {

}
