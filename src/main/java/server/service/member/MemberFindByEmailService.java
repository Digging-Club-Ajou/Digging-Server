package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.repository.member.MemberRepository;

import java.util.Optional;

@Service
public class MemberFindByEmailService {

    private final MemberRepository memberRepository;

    public MemberFindByEmailService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }
}
