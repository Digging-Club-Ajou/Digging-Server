package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.repository.member.MemberRepository;

import java.util.Optional;

@Service
public class MemberFindByLoginService {

    private final MemberRepository memberRepository;

    public MemberFindByLoginService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByLoginId(final String loginId) {
        return memberRepository.findByLoginId(loginId);
    }
}
