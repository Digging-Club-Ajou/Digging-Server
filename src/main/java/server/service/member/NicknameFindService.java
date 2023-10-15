package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.repository.member.MemberRepository;

@Service
public class NicknameFindService {

    private final MemberRepository memberRepository;

    public NicknameFindService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public String findNickname(final long memberId) {
        Member member = memberRepository.getById(memberId);
        return member.getNickname();
    }
}
