package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.repository.member.MemberRepository;

import java.util.Optional;

@Service
public class MemberFindByKakaoIdService {

    private final MemberRepository memberRepository;

    public MemberFindByKakaoIdService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByKakaoId(final long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }
}
