package server.service.member_search;

import org.springframework.stereotype.Service;
import server.domain.member.persist.Member;
import server.mapper.memeber_search.dto.MemberSearchResponse;
import server.mapper.memeber_search.dto.MemberSearchResponses;
import server.repository.member.MemberRepository;
import server.service.album.prod.AlbumFindProdService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberSearchService {

    private MemberRepository memberRepository;
    private AlbumFindProdService albumFindProdService;
    public MemberSearchService(final MemberRepository memberRepository, final AlbumFindProdService albumFindProdService){
        this.memberRepository = memberRepository;
        this.albumFindProdService = albumFindProdService;
    }


    public MemberSearchResponses getMemberSearchList(final String keyword){
        List<Member> members = memberRepository.findByNicknameContaining(keyword);
        List<MemberSearchResponse> memberSearchResponses = new ArrayList<>();

        System.out.println("members "+members);
        members.forEach(member -> {
            String imageUrl = albumFindProdService.getAlbumUrl(member.getId());
            MemberSearchResponse memberSearchResponse = MemberSearchResponse.builder().imageUrl(imageUrl).nickname(member.getNickname()).build();
            memberSearchResponses.add(memberSearchResponse);
        }
        );

        System.out.println("responses "+memberSearchResponses);
        return MemberSearchResponses.builder().memberSearchResponses(memberSearchResponses).build();
    }
}
