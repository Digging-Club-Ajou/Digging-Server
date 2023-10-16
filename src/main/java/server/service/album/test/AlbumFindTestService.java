package server.service.album.test;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import server.domain.album.Album;
import server.global.exception.NotFoundException;
import server.mapper.album.dto.AlbumResponse;
import server.repository.album.AlbumRepository;
import server.service.album.AlbumFindService;
import server.service.album.AlbumValidationService;

import java.net.URL;
import java.util.Date;

import static server.global.constant.ExceptionMessage.ALBUM_NOT_FOUND_EXCEPTION;
import static server.global.constant.TestConstant.TEST_URL;
import static server.global.constant.TextConstant.ALBUM_IMAGE;
import static server.global.constant.TextConstant.DIGGING_CLUB;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.mapper.album.dto.AlbumResponse.toAlbumResponse;

// Test Double
@Profile("test")
@Service
public class AlbumFindTestService implements AlbumFindService {

    private final AlbumValidationService albumValidationService;
    private final AlbumRepository albumRepository;

    public AlbumFindTestService(final AlbumValidationService albumValidationService,
                                final AlbumRepository albumRepository) {
        this.albumValidationService = albumValidationService;
        this.albumRepository = albumRepository;
    }

    @Override
    public AlbumResponse getAlbumResponse(final long albumId) {
        if (albumValidationService.validateExistByAlbumId(albumId)) {
            Album album = albumRepository.getByAlbumId(albumId);
            return AlbumResponse.toAlbumResponse(album, TEST_URL.value);
        }

        throw new NotFoundException(ALBUM_NOT_FOUND_EXCEPTION.message);
    }
}
