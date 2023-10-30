package server.mapper.following.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record Followings(
        List<FollowingResponse> followings,
        List<FollowingResponse> followeds
) {
}
