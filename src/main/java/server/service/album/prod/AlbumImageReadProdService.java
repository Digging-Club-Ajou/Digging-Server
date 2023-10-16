package server.service.album.prod;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import server.global.exception.NotFoundException;
import server.service.album.AlbumImageReadService;
import server.service.album.AlbumValidationService;

import java.net.URL;
import java.util.Date;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.TextConstant.ALBUM_IMAGE;
import static server.global.constant.TextConstant.DIGGING_CLUB;
import static server.global.constant.TimeConstant.*;

@Profile({"prod", "dev"})
@Service
public class AlbumImageReadProdService implements AlbumImageReadService {

    private final AmazonS3 amazonS3Client;
    private final AlbumValidationService albumValidationService;

    public AlbumImageReadProdService(final AmazonS3 amazonS3Client, final AlbumValidationService albumValidationService) {
        this.amazonS3Client = amazonS3Client;
        this.albumValidationService = albumValidationService;
    }

    public String getAlbumImageUrl(final long memberId) {
        if (albumValidationService.validateExistByMemberId(memberId)) {
            Date expiration = new Date();
            long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(DIGGING_CLUB.value, ALBUM_IMAGE.value + memberId)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        }

        throw new NotFoundException(ALBUM_IMAGE_NOT_FOUND_EXCEPTION.message);
    }
}
