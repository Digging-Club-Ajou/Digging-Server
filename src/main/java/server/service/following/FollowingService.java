package server.service.following;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.domain.member.persist.Member;
import server.mapper.album.dto.AlbumResponse;
import server.mapper.following.dto.FollowingDto;
import server.mapper.following.dto.FollowingResponse;
import server.mapper.following.dto.Followings;
import server.repository.album.AlbumRepository;
import server.repository.following.FollowingRepository;
import server.repository.member.MemberRepository;
import server.service.album.prod.AlbumFindProdService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FollowingService {

    private FollowingRepository followingRepository;
    private MemberRepository memberRepository;
    private AlbumRepository albumRepository;
    private AlbumFindProdService albumFindService;

    public FollowingService(final FollowingRepository followingRepository, final MemberRepository memberRepository,
                            final AlbumRepository albumRepository, final AlbumFindProdService albumFindService){
        this.followingRepository = followingRepository;
        this.memberRepository = memberRepository;
        this.albumRepository = albumRepository;
        this.albumFindService = albumFindService;
    }


    @Transactional
    public void saveFollowing(Long memberId, FollowingDto followingDto){
        FollowingInfo followingInfo = FollowingInfo.builder().followingId(followingDto.followingId()).followedId(memberId).build();
        followingRepository.save(followingInfo);
    }

    @Transactional
    public void deleteFollowing(Long followingInfoId){
        followingRepository.delete(followingInfoId);
    }

    @Transactional
    public Followings getFollowingList(Long memberId){

        List<FollowingInfo> followingList= followingRepository.findAllByFollowingId(memberId);
        List<FollowingInfo> followerList = followingRepository.findAllByFollowedId(memberId);

        List<FollowingResponse> followingResponses =  new ArrayList<>();
        List<FollowingResponse> followedResponses =  new ArrayList<>();

        followerList.forEach( followInfo -> {
                    String imageUrl = albumFindService.getAlbumUrl(memberId);
                    FollowingResponse follow = FollowingResponse.builder()
                            .followingInfoId(followInfo.getId())
                            .imageUrl(imageUrl)
                            .memberId(memberId)
                            .nickname(memberRepository.getById(followInfo.getFollowedId()).getNickname())
                            .build();
                    followedResponses.add(follow);
                }
        );

        followingList.forEach( followInfo -> {
            String imageUrl = albumFindService.getAlbumUrl(memberId);
            Boolean check = followerList.contains(followInfo);
            FollowingResponse follow = FollowingResponse.builder()
                    .followingInfoId(followInfo.getId())
                    .imageUrl(imageUrl)
                    .memberId(memberId)
                    .nickname(memberRepository.getById(followInfo.getFollowingId()).getNickname())
                    .isFollowForFollow(check)
                    .build();
            followingResponses.add(follow);
        });

        return Followings.builder().followings(followingResponses).followeds(followedResponses).build();
    }



}
