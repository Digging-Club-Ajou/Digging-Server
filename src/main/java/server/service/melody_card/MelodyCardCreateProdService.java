package server.service.melody_card;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.domain.album.Album;
import server.domain.melody_card.MelodyCard;
import server.global.exception.BadRequestException;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.repository.album.AlbumRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static server.global.constant.ExceptionMessage.*;
import static server.global.constant.TextConstant.*;
import static server.global.constant.TimeConstant.ONE_HOUR;

@Profile({"prod", "dev"})
@Service
public class MelodyCardCreateProdService implements MelodyCardCreateService {

    private final AmazonS3 amazonS3Client;
    private final MelodyCardInfoCreateService melodyCardInfoCreateService;
    private final MelodyCardFindService melodyCardFindService;
    private final AlbumRepository albumRepository;

    public MelodyCardCreateProdService(final AmazonS3 amazonS3Client,
                                       final MelodyCardInfoCreateService melodyCardInfoCreateService,
                                       final MelodyCardFindService melodyCardFindService,
                                       final AlbumRepository albumRepository) {
        this.amazonS3Client = amazonS3Client;
        this.melodyCardInfoCreateService = melodyCardInfoCreateService;
        this.melodyCardFindService = melodyCardFindService;
        this.albumRepository = albumRepository;
    }

    public void createMelodyCard(final long memberId, final MelodyCardRequest dto,
                                 final MultipartFile melodyCardImage) {

        List<MelodyCard> melodyCards = melodyCardFindService.findMelodyCards(memberId);
        Album album = albumRepository.getByMemberId(memberId);
        MelodyCard melodyCard = melodyCardInfoCreateService.createMelodyCardInfo(album, dto);

        if(melodyCardImage != null)
        {
            ObjectMetadata objectMetadata = getObjectMetadata(melodyCardImage);
            try {
                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        DIGGING_CLUB.value,
                        MELODY_CARD_IMAGE.value + melodyCard.getId(),
                        melodyCardImage.getInputStream(),
                        objectMetadata
                );
                amazonS3Client.putObject(putObjectRequest);

            } catch (IOException e) {
                throw new BadRequestException(PROFILES_SAVE_EXCEPTION.message);
            }
        }

    }

    public MelodyCardResponse getMelodyCard(final long melodyCardId) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(DIGGING_CLUB.value, MELODY_CARD_IMAGE.value + melodyCardId)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
        MelodyCardResponse melodyCardResponse =
                melodyCardFindService.findMelodyCardResponse(melodyCardId);

        return melodyCardResponse.updateUrl(url.toString());
    }

    public List<MelodyCardResponse>  getMelodyCards(final long albumId) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
        expiration.setTime(expTimeMillis);

        List<MelodyCard> melodyCards = melodyCardFindService.findMelodyCards(albumId);

        List<MelodyCardResponse> melodyCardResponses = new ArrayList<>();
        for (MelodyCard melodyCard : melodyCards) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(DIGGING_CLUB.value, MELODY_CARD_IMAGE.value
                            + melodyCard.getId())
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
            MelodyCardResponse melodyCardResponse =
                    melodyCardFindService.findMelodyCardResponse(melodyCard.getId());

            melodyCardResponses.add(melodyCardResponse.updateUrl(url.toString()));
        }

        return melodyCardResponses;
    }

    private ObjectMetadata getObjectMetadata(final MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }
}
