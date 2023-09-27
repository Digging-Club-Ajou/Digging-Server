package server.service.profile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.global.exception.BadRequestException;

import java.io.IOException;

import static server.global.constant.ExceptionMessage.PROFILES_SAVE_EXCEPTION;

@Service
public class ProfileCreateService {

//    private final AmazonS3Client amazonS3Client;
//    private final ProfileInfoSaveService profileInfoSaveService;
//
//    public ProfileCreateService(final AmazonS3Client amazonS3Client,
//                                final ProfileInfoSaveService profileInfoSaveService) {
//        this.amazonS3Client = amazonS3Client;
//        this.profileInfoSaveService = profileInfoSaveService;
//    }
//
//    public void createProfiles(final long memberId, final MultipartFile multipartFile) {
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentType(multipartFile.getContentType());
//        objectMetadata.setContentLength(multipartFile.getSize());
//
//        try {
//            PutObjectRequest putObjectRequest = new PutObjectRequest(
//                    "digging-club",
//                    String.valueOf(memberId),
//                    multipartFile.getInputStream(),
//                    objectMetadata
//            );
//
//            amazonS3Client.putObject(putObjectRequest);
//            profileInfoSaveService.saveProfileInfo(memberId);
//        } catch (IOException e) {
//            throw new BadRequestException(PROFILES_SAVE_EXCEPTION.message);
//        }
//    }
}
