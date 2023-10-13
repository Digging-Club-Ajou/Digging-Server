package server.service.melodyCard;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.domain.album.Album;
import server.domain.melodyCard.MelodyCard;
import server.domain.member.vo.MemberSession;
import server.global.exception.BadRequestException;
import server.mapper.melodyCard.dto.MelodyCardRequest;
import server.repository.album.AlbumRepository;
import server.repository.melodyCard.MelodyCardRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.TextConstant.*;
import static server.global.constant.TimeConstant.ONE_HOUR;

@Service
public class MelodyCardCreateService implements MelodyCardReadService {

    private final AmazonS3 amazonS3Client;
    private final MelodyCardInfoCreateService melodyCardInfoCreateService;
    private final MelodyCardRepository melodyCardRepository;
    private final AlbumRepository albumRepository;

    public MelodyCardCreateService(final AmazonS3 amazonS3Client, final MelodyCardRepository melodyCardRepository,
                                   final AlbumRepository albumRepository, final MelodyCardInfoCreateService melodyCardInfoCreateService) {
        this.amazonS3Client = amazonS3Client;
        this.melodyCardRepository = melodyCardRepository;
        this.albumRepository = albumRepository;
        this.melodyCardInfoCreateService = melodyCardInfoCreateService;
    }


    @Transactional
    public void createMelodyCard(final MemberSession memberSession, final MelodyCardRequest dto, final MultipartFile melodyCardImage) {

        Optional<Album> albums = albumRepository.findByMemberId(memberSession.id());

        if(albums.isEmpty()){
            throw new BadRequestException(ALBUM_NOT_EXIST.message);

        }

        Album album = albumRepository.getByMemberId(memberSession.id());
        Optional<MelodyCard> melodyCards = melodyCardRepository.findByAlbumId(album.getId());


        if(melodyCards.stream().count()>10){
            throw new BadRequestException(MELODY_CARD_LIMIT.message);
        }

        ObjectMetadata objectMetadata = getObjectMetadata(melodyCardImage);
        try {

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    DIGGING_CLUB.value,
                    MELODYCARD_IMAGE.value + album.getId(),
                    melodyCardImage.getInputStream(),
                    objectMetadata
            );

            amazonS3Client.putObject(putObjectRequest);
            melodyCardInfoCreateService.createMelodyCardInfo(album,dto);

        } catch (IOException e) {
            throw new BadRequestException(PROFILES_SAVE_EXCEPTION.message);
        }

    }


    @Override
    public String getMelodyCardImageUrl(long memberId) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(DIGGING_CLUB.value, MELODYCARD_IMAGE.value + memberId)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }


    private ObjectMetadata getObjectMetadata(final MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }

}
