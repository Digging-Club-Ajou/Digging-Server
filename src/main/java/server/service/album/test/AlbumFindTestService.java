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


import java.util.ArrayList;
import java.util.List;

import static server.global.constant.ExceptionMessage.ALBUM_NOT_FOUND_EXCEPTION;
import static server.global.constant.TestConstant.TEST_URL;

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
        List<String> artistNames = new ArrayList<>();
        artistNames.add("artist1");
        artistNames.add("artist2");
        artistNames.add("artist3");

        if (albumValidationService.validateExistByAlbumId(albumId)) {
            Album album = albumRepository.getByAlbumId(albumId);
            return AlbumResponse.toAlbumResponse(album, TEST_URL.value, artistNames);
        }

        throw new NotFoundException(ALBUM_NOT_FOUND_EXCEPTION.message);
    }
}
