package server.service.member.nickname;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.global.constant.ExceptionMessage;
import server.global.exception.BadRequestException;
import server.global.exception.NotFoundException;
import server.repository.member.MemberRepository;

import static server.global.constant.ExceptionMessage.NICKNAME_NOT_FOUND_EXCEPTION;

@Service
public class NicknameFindService {

    private final MemberRepository memberRepository;

    public NicknameFindService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public String findNickname(final long memberId) {
        Member member = memberRepository.getById(memberId);
        String nickname = member.getNickname();

        if (nickname == null) {
            throw new NotFoundException(NICKNAME_NOT_FOUND_EXCEPTION.message);
        }

        return nickname;
    }
}
