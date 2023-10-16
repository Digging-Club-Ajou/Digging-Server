package server.service.album.prod;

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
import static server.global.constant.TextConstant.ALBUM_IMAGE;
import static server.global.constant.TextConstant.DIGGING_CLUB;
import static server.global.constant.TimeConstant.ONE_HOUR;
import static server.mapper.album.dto.AlbumResponse.toAlbumResponse;

@Profile({"prod", "dev"})
@Service
public class AlbumFindProdService implements AlbumFindService {

    private final AmazonS3 amazonS3Client;
    private final AlbumValidationService albumValidationService;
    private final AlbumRepository albumRepository;

    public AlbumFindProdService(final AmazonS3 amazonS3Client, final AlbumValidationService albumValidationService,
                                final AlbumRepository albumRepository) {
        this.amazonS3Client = amazonS3Client;
        this.albumValidationService = albumValidationService;
        this.albumRepository = albumRepository;
    }

    public AlbumResponse getAlbumResponse(final long albumId) {
        if (albumValidationService.validateExistByAlbumId(albumId)) {
            Date expiration = new Date();
            long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(
                            DIGGING_CLUB.value,
                            ALBUM_IMAGE.value + albumId)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            String imageUrl = url.toString();

            Album album = albumRepository.getByAlbumId(albumId);
            return toAlbumResponse(album, imageUrl);
        }

        throw new NotFoundException(ALBUM_NOT_FOUND_EXCEPTION.message);
    }
}
