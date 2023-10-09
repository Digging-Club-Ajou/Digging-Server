package server.mapper.member.dto;

import server.global.exception.BadRequestException;

import java.util.regex.Pattern;

import static server.global.constant.ExceptionMessage.NICKNAME_REGEX_EXCEPTION;

public record NicknameRequest(
        String nickname
) {

    private static final String NICKNAME_REGEXP = "^[a-z0-9_.]{2,20}$";
    private static final String SMALL_LETTER_REGEXP = "[a-z]";
    private static final String NUMBER_REGEXP = "[0-9]";
    private static final String UNDERSCORE_REGEXP = "_";
    private static final String DOT_REGEXP = ".";

    public void validateRegex() {
        if (!Pattern.matches(NICKNAME_REGEXP, nickname)) {
            throw new BadRequestException(NICKNAME_REGEX_EXCEPTION.message);
        }

        int countTypes = 0;

        if (Pattern.compile(SMALL_LETTER_REGEXP).matcher(nickname).find()) {
            countTypes++;
        }

        if (Pattern.compile(NUMBER_REGEXP).matcher(nickname).find()) {
            countTypes++;
        }

        if (nickname.contains(UNDERSCORE_REGEXP)){
            countTypes++;
        }

        if (nickname.contains(DOT_REGEXP)){
            countTypes++;
        }

        if(countTypes < 2){
            throw new BadRequestException(NICKNAME_REGEX_EXCEPTION.message);
        }
    }
}
