package server.service.following;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.global.exception.BadRequestException;
import server.mapper.album.dto.AlbumResponse;
import server.mapper.following.dto.FollowingDto;
import server.mapper.following.dto.FollowingInfoDto;
import server.mapper.following.dto.FollowingResponse;
import server.mapper.following.dto.Followings;
import server.repository.album.AlbumRepository;
import server.repository.following.FollowingRepository;
import server.repository.member.MemberRepository;
import server.service.album.AlbumFindService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowingService {

    private FollowingRepository followingRepository;
    private MemberRepository memberRepository;
    private AlbumRepository albumRepository;
    private AlbumFindService albumFindService;

    public FollowingService(final FollowingRepository followingRepository, final MemberRepository memberRepository,
                            final AlbumRepository albumRepository, final AlbumFindService albumFindService) {
        this.followingRepository = followingRepository;
        this.memberRepository = memberRepository;
        this.albumRepository = albumRepository;
        this.albumFindService = albumFindService;
    }

    @Transactional
    public void saveFollowing(Long memberId, FollowingDto followingDto){
        FollowingInfo followingInfo = FollowingInfo.builder().followedId(followingDto.memberId()).followingId(memberId).build();
        followingRepository.save(followingInfo);
    }

    @Transactional
    public void deleteFollowing(FollowingInfoDto followingInfoDto){
        FollowingInfo followingInfo = followingRepository.findByFollowingIdAndFollowedId(followingInfoDto.followingId(), followingInfoDto.followedId());
        followingRepository.delete(followingInfo);
    }

    @Transactional
    public Followings getFollowingList(Long memberId){

        List<FollowingInfo> followingList= followingRepository.findAllByFollowingId(memberId);
        List<FollowingInfo> followerList = followingRepository.findAllByFollowedId(memberId);

        List<FollowingResponse> followingResponses =  new ArrayList<>();
        List<FollowingResponse> followerResponses =  new ArrayList<>();

        followerList.forEach( followInfo -> {

            Long albumId;
            try {
                Album album = albumRepository.getByMemberId(followInfo.getFollowingId());
                albumId = album.getId();

            }catch (BadRequestException e){
                albumId =null;
            }

            FollowingResponse follow = FollowingResponse.builder()
                    .memberId(followInfo.getFollowingId())
                    .albumId(albumId)
                    .nickname(memberRepository.getById(followInfo.getFollowingId()).getNickname())
                    .build();
            followerResponses.add(follow);

            }
        );

        followingList.forEach( followInfo -> {

            Long albumId;
            try {
                Album album = albumRepository.getByMemberId(followInfo.getFollowedId());
                albumId = album.getId();

            }catch (BadRequestException e){
                albumId =null;
            }

            Boolean check = followerList.contains(followInfo);

            FollowingResponse follow = FollowingResponse.builder()
                    .memberId(followInfo.getFollowedId())
                    .albumId(albumId)
                    .nickname(memberRepository.getById(followInfo.getFollowedId()).getNickname())
                    .isFollowForFollow(check)
                    .build();

            followingResponses.add(follow);

        });

        return Followings.builder().followings(followingResponses).followers(followerResponses).build();
    }



}
