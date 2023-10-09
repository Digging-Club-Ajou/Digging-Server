package server.mapper.album.dto;


import server.global.exception.BadRequestException;
import server.global.exception.dto.ResultResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static server.global.constant.AnnotationValidConstant.ALBUM_NAME_REGEXP;
import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.StatusCodeConstant.*;

public record AlbumNameRequest(
        String albumName
) {

        public ResultResponse validateRegex() {
                Pattern pattern = java.util.regex.Pattern.compile("[^\\p{L}\\p{N}\\p{P}\\p{Z}]",
                        Pattern.UNICODE_CASE |
                                Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(albumName);

                if (matcher.find()) {
                        throw new BadRequestException(ALBUM_EMOTICONS_REGEX_EXCEPTION.message);
                }

                if (!Pattern.matches(ALBUM_NAME_REGEXP, albumName)) {
                        throw new BadRequestException(ALBUM_NAME_REGEX.message);
                }

                return new ResultResponse(IS_OK.statusCode, ALBUM_NAME_REGEX.message);
        }
}
