package server.service.album;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.domain.album.Album;
import server.domain.member.vo.MemberSession;
import server.global.exception.BadRequestException;
import server.mapper.album.dto.AlbumNameRequest;

import java.io.IOException;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.ExceptionMessage.PROFILES_SAVE_EXCEPTION;
import static server.global.constant.TextConstant.ALBUM_IMAGE;
import static server.global.constant.TextConstant.DIGGING_CLUB;

@Service
public class AlbumCreateService {

    private final AmazonS3Client amazonS3Client;
    private final AlbumValidationService albumValidationService;
    private final AlbumInfoCreateService albumInfoCreateService;

    public AlbumCreateService(final AmazonS3Client amazonS3Client,
                              final AlbumValidationService albumValidationService,
                              final AlbumInfoCreateService albumInfoCreateService) {
        this.amazonS3Client = amazonS3Client;
        this.albumValidationService = albumValidationService;
        this.albumInfoCreateService = albumInfoCreateService;
    }

    public void createAlbum(final MemberSession memberSession, final AlbumNameRequest dto,
                            final MultipartFile albumImage) {

        dto.validateRegex();
        String albumName = dto.albumName();
        validateExist(memberSession.id());
        Album album = albumInfoCreateService.createProfileInfo(memberSession, albumName);

        if (albumImage!= null){
            ObjectMetadata objectMetadata = getObjectMetadata(albumImage);

            try {
                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        DIGGING_CLUB.value,
                        ALBUM_IMAGE.value + album.getId(),
                        albumImage.getInputStream(),
                        objectMetadata
                );

                amazonS3Client.putObject(putObjectRequest);

            } catch (IOException e) {
                throw new BadRequestException(PROFILES_SAVE_EXCEPTION.message);
            }
        }


    }

    private void validateExist(final long memberId) {
        if (albumValidationService.validateExistByMemberId(memberId)) {
            throw new BadRequestException(ALBUM_ALREADY_EXIST_EXCEPTION.message);
        }
    }

    private ObjectMetadata getObjectMetadata(final MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }
}
