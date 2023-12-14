package server.service.member.nickname;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.global.exception.BadRequestException;
import server.global.exception.dto.ResultResponse;
import server.mapper.member.dto.NicknameRequest;
import server.repository.member.MemberRepository;

import java.util.Optional;

import static server.global.constant.ExceptionMessage.NICKNAME_CAN_USE;
import static server.global.constant.ExceptionMessage.NICKNAME_DUPLICATE_EXCEPTION;
import static server.global.constant.StatusCodeConstant.IS_OK;

@Service
public class NicknameValidationService {

    private final MemberRepository memberRepository;

    public NicknameValidationService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public ResultResponse validateNickname(final NicknameRequest dto) {
        dto.validateRegex();

        Optional<Member> optionalMember = memberRepository.findByNickname(dto.nickname());
        if (optionalMember.isPresent()) {
            throw new BadRequestException(NICKNAME_DUPLICATE_EXCEPTION.message);
        }

        return new ResultResponse(IS_OK.statusCode, NICKNAME_CAN_USE.message);
    }
}
