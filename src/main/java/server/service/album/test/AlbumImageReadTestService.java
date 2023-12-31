package server.service.album.test;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import server.global.exception.NotFoundException;
import server.service.album.AlbumImageReadService;
import server.service.album.AlbumValidationService;


import static server.global.constant.ExceptionMessage.ALBUM_IMAGE_NOT_FOUND_EXCEPTION;
import static server.global.constant.TestConstant.*;

// Test Double
@Profile("test")
@Service
public class AlbumImageReadTestService implements AlbumImageReadService {

    private final AlbumValidationService albumValidationService;

    public AlbumImageReadTestService(final AlbumValidationService albumValidationService) {
        this.albumValidationService = albumValidationService;
    }

    public String getAlbumImageUrl(final long memberId) {
        if (albumValidationService.validateExistByMemberId(memberId)) {
            return TEST_URL.value;
        }

        throw new NotFoundException(ALBUM_IMAGE_NOT_FOUND_EXCEPTION.message);
    }
}
