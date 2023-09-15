package server.application.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.global.exception.BadRequestException;
import server.mapper.member.MemberMapper;
import server.mapper.member.dto.MemberSignupRequest;
import server.repository.member.MemberRepository;

import static server.global.constant.ExceptionMessage.*;

@Service
public class MemberSignupService {

    private final MemberRepository memberRepository;

    public MemberSignupService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void signup(final MemberSignupRequest dto) {
        validatePassword(dto);
        Member member = MemberMapper.toEntity(dto);
        memberRepository.save(member);
    }

    private void validatePassword(final MemberSignupRequest dto) {
        String password = dto.password();
        String passwordCheck = dto.passwordCheck();
        if (!password.equals(passwordCheck)) {
            throw new BadRequestException(PASSWORD_NOT_MATCH_EXCEPTION.message);
        }
    }
}
