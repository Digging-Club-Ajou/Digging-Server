package server.service.member.nickname;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.global.exception.BadRequestException;
import server.mapper.member.dto.NicknameRequest;
import server.mapper.member.dto.UserInfoRequest;
import server.repository.member.MemberRepository;

import java.util.Optional;

import static server.global.constant.ExceptionMessage.*;

@Service
public class NicknameCreateService {

    private final MemberRepository memberRepository;

    public NicknameCreateService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createNickname(final long memberId, final NicknameRequest dto) {
        dto.validateRegex();

        Optional<Member> optionalMember = memberRepository.findByNickname(dto.nickname());
        if (optionalMember.isPresent()) {
            throw new BadRequestException(NICKNAME_DUPLICATE_EXCEPTION.message);
        }
        Member member = memberRepository.getById(memberId);
        member.createNickname(dto.nickname());
    }



}
