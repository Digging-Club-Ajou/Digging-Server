package server.service.member;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.global.exception.BadRequestException;

import java.io.IOException;

import static server.global.constant.ExceptionMessage.PROFILES_SAVE_EXCEPTION;

@Service
public class ProfilesSaveService {

    public void saveProfiles(final long memberId, final MultipartFile multipartFile,
                             final AmazonS3 amazonS3Client) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    "digging-club",
                    String.valueOf(memberId),
                    multipartFile.getInputStream(),
                    objectMetadata
            );

            amazonS3Client.putObject(putObjectRequest);

        } catch (IOException e) {
            throw new BadRequestException(PROFILES_SAVE_EXCEPTION.message);
        }
    }
}
