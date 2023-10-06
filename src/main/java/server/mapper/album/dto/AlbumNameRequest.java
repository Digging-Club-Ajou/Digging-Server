package server.mapper.album.dto;

import jakarta.validation.constraints.Pattern;

import static server.global.constant.AnnotationValidConstant.*;

public record AlbumNameRequest(
        @Pattern(regexp = ALBUM_NAME_REGEXP, message = ALBUM_NAME_VALID_MESSAGE)
        String albumName
) {
}
