package server.service.member_search;

import org.springframework.stereotype.Service;
import server.domain.member.persist.Member;
import server.mapper.memeber_search.dto.MemberSearchResponse;
import server.mapper.memeber_search.dto.MemberSearchResponses;
import server.repository.member.MemberRepository;
import server.service.album.prod.AlbumFindProdService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MemberSearchService {

    private MemberRepository memberRepository;
    private AlbumFindProdService albumFindProdService;
    public MemberSearchService(final MemberRepository memberRepository, final AlbumFindProdService albumFindProdService){
        this.memberRepository = memberRepository;
        this.albumFindProdService = albumFindProdService;
    }


    public MemberSearchResponses getMemberSearchList(final String keyword){
        List<Member> members = getMembers(keyword);
        List<MemberSearchResponse> memberSearchResponses = new ArrayList<>();

        members.forEach(member -> {
            String imageUrl = albumFindProdService.getAlbumUrl(member.getId());
            MemberSearchResponse memberSearchResponse = MemberSearchResponse.builder().imageUrl(imageUrl).nickname(member.getNickname()).build();
            memberSearchResponses.add(memberSearchResponse);
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
