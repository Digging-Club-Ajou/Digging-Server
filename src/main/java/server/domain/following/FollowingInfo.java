package server.domain.following;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.BaseTimeEntity;
import server.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FollowingInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "following_info_id")
    private Long id;

    private Long followedId;
    private Long followingId;


    @Builder
    public FollowingInfo(final Long followedId, final Long followingId){
        this.followingId = followingId;
        this.followedId = followedId;
    }
}
