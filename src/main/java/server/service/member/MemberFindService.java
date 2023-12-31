package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.MemberResponse;
import server.repository.album.AlbumRepository;
import server.repository.member.MemberRepository;

import java.util.Optional;

@Service
public class MemberFindService {

    private final MemberRepository memberRepository;
    private final AlbumRepository albumRepository;

    public MemberFindService(final MemberRepository memberRepository,
                             final AlbumRepository albumRepository) {
        this.memberRepository = memberRepository;
        this.albumRepository = albumRepository;
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(final MemberSession memberSession) {
        Member member = memberRepository.getById(memberSession.id());
        Optional<Album> optionalAlbum = albumRepository.findByMemberId(memberSession.id());
        if (optionalAlbum.isPresent()) {
            Album album = optionalAlbum.get();
            return MemberMapper.toMemberResponse(member, album.getId());
        } else {
            return MemberMapper.toMemberResponseWithoutAlbum(member);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByKakaoId(final long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }
}
