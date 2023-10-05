package server.service.album;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.global.exception.BadRequestException;

import java.io.IOException;

import static server.global.constant.ExceptionMessage.PROFILES_SAVE_EXCEPTION;
import static server.global.constant.TextConstant.DIGGING_CLUB;

@Service
public class AlbumCreateService {

    private final AmazonS3Client amazonS3Client;
    private final AlbumInfoCreateService albumInfoCreateService;

    public AlbumCreateService(final AmazonS3Client amazonS3Client,
                              final AlbumInfoCreateService albumInfoCreateService) {
        this.amazonS3Client = amazonS3Client;
        this.albumInfoCreateService = albumInfoCreateService;
    }

    public void createAlbum(final long memberId, final String albumName, final MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = getObjectMetadata(multipartFile);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    DIGGING_CLUB.value,
                    String.valueOf(memberId),
                    multipartFile.getInputStream(),
                    objectMetadata
            );

            amazonS3Client.putObject(putObjectRequest);
            albumInfoCreateService.createProfileInfo(memberId, albumName);
        } catch (IOException e) {
            throw new BadRequestException(PROFILES_SAVE_EXCEPTION.message);
        }
    }

    private ObjectMetadata getObjectMetadata(final MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }
}
