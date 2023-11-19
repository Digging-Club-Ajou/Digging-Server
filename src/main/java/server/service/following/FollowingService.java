package server.service.following;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.following.FollowingInfo;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.global.exception.BadRequestException;
import server.mapper.following.dto.FollowingDto;
import server.mapper.following.dto.FollowingInfoDto;
import server.mapper.following.dto.FollowingResponse;
import server.mapper.following.dto.Followings;
import server.repository.album.AlbumRepository;
import server.repository.following.FollowingRepository;
import server.repository.member.MemberRepository;
import server.service.album.AlbumFindService;
import server.service.notification.NotificationCreateService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static server.global.constant.ExceptionMessage.FOLLOWING_ALREADY_EXISTS;
import static server.global.constant.ExceptionMessage.FOLLOWING_NOT_FOUND;
import static server.global.constant.NotificationConstant.FOLLOWING_NOTIFICATION;

@Service
public class FollowingService {

    private FollowingRepository followingRepository;
    private MemberRepository memberRepository;
    private AlbumRepository albumRepository;
    private AlbumFindService albumFindService;
    private final NotificationCreateService notificationCreateService;

    public FollowingService(final FollowingRepository followingRepository, final MemberRepository memberRepository,
                            final AlbumRepository albumRepository, final AlbumFindService albumFindService,
                            final NotificationCreateService notificationCreateService) {
        this.followingRepository = followingRepository;
        this.memberRepository = memberRepository;
        this.albumRepository = albumRepository;
        this.albumFindService = albumFindService;
        this.notificationCreateService = notificationCreateService;
    }

    @Transactional
    public void saveFollowing(Long memberId, FollowingDto followingDto){

        FollowingInfo following = followingRepository.findByFollowingIdAndFollowedId(memberId,followingDto.memberId());

        if(following != null){
            throw new BadRequestException(FOLLOWING_ALREADY_EXISTS.message);
        }

        FollowingInfo followingInfo = FollowingInfo.builder().followedId(followingDto.memberId()).followingId(memberId).build();
        followingRepository.save(followingInfo);
        notification(memberId, followingDto);




    }

    private void notification(final long memberId, final FollowingDto followingDto) {
        Member member = memberRepository.getById(memberId);
        String notificationMessage = member.getNickname() + FOLLOWING_NOTIFICATION;
        notificationCreateService.saveNotificationMessage(followingDto.memberId(), notificationMessage);
    }

    @Transactional
    public void deleteFollowing(FollowingInfoDto followingInfoDto){
        FollowingInfo followingInfo = followingRepository.findByFollowingIdAndFollowedId(followingInfoDto.followingId(), followingInfoDto.followedId());
        if(followingInfo == null){
            throw new BadRequestException(FOLLOWING_NOT_FOUND.message);
        }
        followingRepository.delete(followingInfo);
    }

    @Transactional
    public Followings getFollowingListByMemberId(Long memberSessionId, Long memberId){

        List<FollowingInfo> followingListByMemberSession = followingRepository.findAllByFollowingId(memberSessionId);
        List<FollowingInfo> followerListByMemberSession = followingRepository.findAllByFollowedId(memberSessionId);

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
                albumId = null;
            }

            Boolean isFollowing = !followingListByMemberSession.stream().filter( info -> info.getFollowedId().equals(followInfo.getFollowingId())).collect(Collectors.toList()).isEmpty();
            Boolean isFollower = !followerListByMemberSession.stream().filter( info -> info.getFollowingId().equals(followInfo.getFollowingId())).collect(Collectors.toList()).isEmpty();

            FollowingResponse follow = FollowingResponse.builder()
                    .memberId(followInfo.getFollowingId())
                    .albumId(albumId)
                    .nickname(memberRepository.getById(followInfo.getFollowingId()).getNickname())
                    .isFollower(isFollower)
                    .isFollowing(isFollowing)
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

            Boolean isFollowing = !followingListByMemberSession.stream().filter(info -> info.getFollowedId().equals(followInfo.getFollowedId())).collect(Collectors.toList()).isEmpty();
            Boolean isFollower = !followerListByMemberSession.stream().filter(info -> info.getFollowingId().equals(followInfo.getFollowedId())).collect(Collectors.toList()).isEmpty();

            FollowingResponse follow = FollowingResponse.builder()
                    .memberId(followInfo.getFollowedId())
                    .albumId(albumId)
                    .nickname(memberRepository.getById(followInfo.getFollowedId()).getNickname())
                    .isFollowing(isFollowing)
                    .isFollower(isFollower)
                    .build();

            followingResponses.add(follow);

        });

        return Followings.builder().followings(followingResponses).followers(followerResponses).build();
    }

    @Transactional
    public Followings getFollowingListByLogin(Long memberId){


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
                        albumId = null;
                    }

                    Boolean isFollowing = !followingList.stream().filter(info -> info.getFollowedId().equals(followInfo.getFollowingId())).collect(Collectors.toList()).isEmpty();

                    FollowingResponse follow = FollowingResponse.builder()
                            .memberId(followInfo.getFollowingId())
                            .albumId(albumId)
                            .nickname(memberRepository.getById(followInfo.getFollowingId()).getNickname())
                            .isFollowing(isFollowing)
                            .isFollower(true)
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

            Boolean isFollower = !followerList.stream().filter(info -> info.getFollowingId().equals(followInfo.getFollowedId())).collect(Collectors.toList()).isEmpty();

            FollowingResponse follow = FollowingResponse.builder()
                    .memberId(followInfo.getFollowedId())
                    .albumId(albumId)
                    .nickname(memberRepository.getById(followInfo.getFollowedId()).getNickname())
                    .isFollower(isFollower)
                    .isFollowing(true)
                    .build();

            followingResponses.add(follow);

        });

        return Followings.builder().followings(followingResponses).followers(followerResponses).build();
    }

    @Transactional
    public boolean getFollowing(MemberSession memberSession, Long memberId){
        try {
            FollowingInfo followingInfo = followingRepository.findByFollowingIdAndFollowedId(memberSession.id(),memberId);
            if(followingInfo == null){
                throw new BadRequestException(FOLLOWING_NOT_FOUND.message);
            }
            return true;
        }catch (BadRequestException e){
            return false;
        }
    }

}
