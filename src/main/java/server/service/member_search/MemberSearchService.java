package server.service.member_search;

import org.springframework.stereotype.Service;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.global.exception.BadRequestException;
import server.mapper.album.dto.AlbumResponse;
import server.mapper.memeber_search.dto.MemberSearchResponse;
import server.mapper.memeber_search.dto.MemberSearchResponses;
import server.repository.album.AlbumRepository;
import server.repository.member.MemberRepository;
import server.service.album.AlbumFindService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MemberSearchService {

    private MemberRepository memberRepository;
    private AlbumFindService albumFindService;
    private AlbumRepository albumRepository;

    public MemberSearchService(final MemberRepository memberRepository, final AlbumFindService albumFindService,
                               final AlbumRepository albumRepository) {
        this.memberRepository = memberRepository;
        this.albumFindService = albumFindService;
        this.albumRepository = albumRepository;
    }

    public MemberSearchResponses getMemberSearchList(final String keyword){
        List<Member> members = getMembers(keyword);
        List<MemberSearchResponse> memberSearchResponses = new ArrayList<>();

        members.forEach(member -> {
            try {
                Album album = albumRepository.getByMemberId(member.getId());
                MemberSearchResponse memberSearchResponse = MemberSearchResponse.builder().nickname(member.getNickname()).albumId(album.getId()).build();
                memberSearchResponses.add(memberSearchResponse);
            }catch (BadRequestException e){

            }

        }
        );

        return MemberSearchResponses.builder().memberSearchResponses(memberSearchResponses).build();
    }

    public List<Member> getMembers(final String keyword){

        List<Member> members = new ArrayList<>();
        List<Member> memberList = Stream.of(memberRepository.findAllByNickName(keyword),memberRepository.findByNicknameStartingWith(keyword),memberRepository.findByNicknameLike("%"+keyword+"%")).flatMap(m -> m.stream()).collect(Collectors.toList());

        memberList.forEach(
                member -> {
                    if(members.stream().filter(member1 -> member1.getId().equals(member.getId())).toList().isEmpty()){
                        members.add(member);
                    }
                }
        );

        return members;
    }
}
