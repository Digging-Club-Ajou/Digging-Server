package server.service.album.test;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.global.exception.BadRequestException;
import server.service.album.AlbumCreateService;
import server.service.album.AlbumInfoCreateService;
import server.service.album.AlbumValidationService;

import static server.global.constant.ExceptionMessage.ALBUM_ALREADY_EXIST_EXCEPTION;

// Test Double
@Profile("test")
@Service
public class AlbumCreateTestService implements AlbumCreateService {

    private final AlbumInfoCreateService albumInfoCreateService;
    private final AlbumValidationService albumValidationService;

    public AlbumCreateTestService(final AlbumInfoCreateService albumInfoCreateService,
                                  final AlbumValidationService albumValidationService) {
        this.albumInfoCreateService = albumInfoCreateService;
        this.albumValidationService = albumValidationService;
    }

    public void createAlbum(final long memberId, final String albumName, final MultipartFile albumImage) {
        validateExist(memberId);
        albumInfoCreateService.createProfileInfo(memberId, albumName);
    }

    private void validateExist(final long memberId) {
        if (albumValidationService.validateAlreadyExist(memberId)) {
            throw new BadRequestException(ALBUM_ALREADY_EXIST_EXCEPTION.message);
        }
    }
}
