package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.mapper.member.dto.UserInfoRequest;
import server.repository.member.MemberRepository;

@Service
public class MemberInfoCreateService {

    private final MemberRepository memberRepository;

    public MemberInfoCreateService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createMemberInfo(final long memberId, final UserInfoRequest dto){
        Member member = memberRepository.getById(memberId);
        member.createGender(dto.gender());
        member.createBirthdate(dto.birthDate());

    }
}
