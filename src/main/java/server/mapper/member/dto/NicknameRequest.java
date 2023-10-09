package server.mapper.member.dto;

import server.global.exception.BadRequestException;

import java.util.regex.Pattern;

import static server.global.constant.ExceptionMessage.NICKNAME_REGEX_EXCEPTION;

public record NicknameRequest(
        String nickname
) {

    private static final String NICKNAME_REGEXP = "^[a-zA-Z0-9_.]{2,20}$";

    public void validateRegex() {
        if (!Pattern.matches(NICKNAME_REGEXP, nickname)) {
            throw new BadRequestException(NICKNAME_REGEX_EXCEPTION.message);
        }
    }
}
