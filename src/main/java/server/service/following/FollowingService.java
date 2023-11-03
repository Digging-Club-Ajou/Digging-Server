package server.service.following;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.domain.member.persist.Member;
import server.global.exception.BadRequestException;
import server.mapper.album.dto.AlbumResponse;
import server.mapper.following.dto.FollowingDto;
import server.mapper.following.dto.FollowingResponse;
import server.mapper.following.dto.Followings;
import server.repository.album.AlbumRepository;
import server.repository.following.FollowingRepository;
import server.repository.member.MemberRepository;
import server.service.album.AlbumFindService;
import server.service.album.prod.AlbumFindProdService;

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
        FollowingInfo followingInfo = FollowingInfo.builder().followedId(followingDto.followingId()).followingId(memberId).build();
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
        List<FollowingResponse> followerResponses =  new ArrayList<>();

        followerList.forEach( followInfo -> {
            String imageUrl = "";
            try{
                Album album = albumRepository.getByMemberId(followInfo.getFollowingId());
                AlbumResponse albumResponse = albumFindService.getAlbumResponse(album.getId());
                imageUrl = albumResponse.imageUrl();
            }catch (BadRequestException e){
                imageUrl = null;
            }finally {
                FollowingResponse follow = FollowingResponse.builder()
                        .followingInfoId(followInfo.getId())
                        .imageUrl(imageUrl)
                        .memberId(followInfo.getFollowingId())
                        .nickname(memberRepository.getById(followInfo.getFollowingId()).getNickname())
                        .build();
                followerResponses.add(follow);
            }
            }
        );

        followingList.forEach( followInfo -> {
            String imageUrl="";
            try{
                Album album = albumRepository.getByMemberId(followInfo.getFollowedId());
                AlbumResponse albumResponse = albumFindService.getAlbumResponse(album.getId());
                imageUrl = albumResponse.imageUrl();
            }catch (BadRequestException e){
                imageUrl = null;
            }finally {
                Boolean check = followerList.contains(followInfo);
                FollowingResponse follow = FollowingResponse.builder()
                        .followingInfoId(followInfo.getId())
                        .imageUrl(imageUrl)
                        .memberId(followInfo.getFollowedId())
                        .nickname(memberRepository.getById(followInfo.getFollowedId()).getNickname())
                        .isFollowForFollow(check)
                        .build();
                followingResponses.add(follow);

            }

        });

        return Followings.builder().followings(followingResponses).followers(followerResponses).build();
    }



}
