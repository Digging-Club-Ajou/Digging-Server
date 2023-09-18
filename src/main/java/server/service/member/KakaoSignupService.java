package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.KakaoSignupRequest;
import server.repository.member.MemberRepository;

@Service
public class KakaoSignupService {

    private final MemberRepository memberRepository;

    public KakaoSignupService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member kakaoSignup(final KakaoSignupRequest dto) {
        Member member = MemberMapper.toEntity(dto);
        return memberRepository.save(member);
    }
}
