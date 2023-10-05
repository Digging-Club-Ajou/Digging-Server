package server.service.album;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.stereotype.Service;
import server.global.exception.BadRequestException;

import java.net.URL;
import java.util.Date;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.TextConstant.DIGGING_CLUB;
import static server.global.constant.TimeConstant.*;

@Service
public class AlbumReadService {

    private final AmazonS3 amazonS3Client;
    private final AlbumValidationService albumValidationService;

    public AlbumReadService(final AmazonS3 amazonS3Client, final AlbumValidationService albumValidationService) {
        this.amazonS3Client = amazonS3Client;
        this.albumValidationService = albumValidationService;
    }

    public String getAlbumImage(final long memberId) {
        if (albumValidationService.validateAlreadyExist(memberId)) {
            Date expiration = new Date();
            long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(DIGGING_CLUB.value, String.valueOf(memberId))
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        }

        throw new BadRequestException(ALBUM_IMAGE_NOT_FOUND_EXCEPTION.message);
    }
}
