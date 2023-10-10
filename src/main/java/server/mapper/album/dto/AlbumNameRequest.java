package server.mapper.album.dto;


import server.global.exception.BadRequestException;
import server.global.exception.dto.ResultResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;
import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.StatusCodeConstant.*;

public record AlbumNameRequest(
        String albumName
) {

        private static final String ALBUM_NAME_REGEXP = "^[a-zA-Z0-9가-힣\\s]{1,15}$";

        public ResultResponse validateRegex() {
                Pattern pattern = compile("[^\\p{L}\\p{N}\\p{Z}]", UNICODE_CASE | CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(albumName);

                if (matcher.find()) {
                        throw new BadRequestException(ALBUM_EMOTICONS_REGEX_EXCEPTION.message);
                }

                if (!matches(ALBUM_NAME_REGEXP, albumName)) {
                        throw new BadRequestException(ALBUM_NAME_REGEX.message);
                }

                return new ResultResponse(IS_OK.statusCode, ALBUM_NAME_REGEX.message);
        }
}
