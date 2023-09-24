package server.mapper.member.dto;

import jakarta.validation.constraints.Pattern;

import static server.global.constant.AnnotationValidConstant.*;

public record NicknameRequest(
        @Pattern(regexp = NICKNAME_REGEXP, message = NICKNAME_VALID_MESSAGE)
        String nickname
) {
}
