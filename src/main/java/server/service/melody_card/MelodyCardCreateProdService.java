package server.service.melody_card;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.domain.album.Album;
import server.domain.melody_card.MelodyCard;
import server.global.exception.BadRequestException;
import server.global.exception.NotFoundException;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.repository.album.AlbumRepository;
import server.service.card_favorite.LikeInfoFindService;

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
    private final LikeInfoFindService likeInfoFindService;

    public MelodyCardCreateProdService(final AmazonS3 amazonS3Client,
                                       final MelodyCardInfoCreateService melodyCardInfoCreateService,
                                       final MelodyCardFindService melodyCardFindService,
                                       final AlbumRepository albumRepository,
                                       final LikeInfoFindService likeInfoFindService) {
        this.amazonS3Client = amazonS3Client;
        this.melodyCardInfoCreateService = melodyCardInfoCreateService;
        this.melodyCardFindService = melodyCardFindService;
        this.albumRepository = albumRepository;
        this.likeInfoFindService = likeInfoFindService;
    }

    @Transactional
    public void createMelodyCard(final long memberId, final MelodyCardRequest melodyCardRequest,
                                 final MultipartFile melodyCardImage) {
        List<MelodyCard> melodyCards = melodyCardFindService.findMelodyCardsByMemberId(memberId);
        Album album = albumRepository.getByMemberId(memberId);

        Boolean isImageUrl = melodyCardImage.getSize() == 0 ? Boolean.FALSE : Boolean.TRUE;
        MelodyCard melodyCard = melodyCardInfoCreateService.createMelodyCardInfo(album, melodyCardRequest,isImageUrl);

        if(isImageUrl)
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
                throw new BadRequestException(MELODY_CARD_EXCEPTION.message);
            }
        }

    }


    public MelodyCardResponse getMelodyCard(final long memberId, final long melodyCardId) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
        expiration.setTime(expTimeMillis);

        MelodyCardResponse melodyCardResponse =
                melodyCardFindService.findMelodyCardResponse(melodyCardId);

        if(melodyCardResponse.getIsImageUrl()){
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(DIGGING_CLUB.value, MELODY_CARD_IMAGE.value + melodyCardId)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
            melodyCardResponse.updateUrl(url.toString());
        }

        Boolean isLike = likeInfoFindService.findLikeInfo(memberId, melodyCardId);
        melodyCardResponse.updateLikeInfo(isLike);

        return melodyCardResponse;
    }

    public List<MelodyCardResponse> getMelodyCards(final long memberId, final long albumId) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
        expiration.setTime(expTimeMillis);

        List<MelodyCard> melodyCards = melodyCardFindService.findMelodyCardsByAlbumId(albumId);

        if(melodyCards.isEmpty()){
            throw new NotFoundException(MELODY_CARD_NOT_FOUND.message);
        }

        List<MelodyCardResponse> melodyCardResponses = new ArrayList<>();
        for (MelodyCard melodyCard : melodyCards) {

            MelodyCardResponse melodyCardResponse =
                    melodyCardFindService.findMelodyCardResponse(melodyCard.getId());

            if(melodyCardResponse.getIsImageUrl()){
                GeneratePresignedUrlRequest generatePresignedUrlRequest =
                        new GeneratePresignedUrlRequest(DIGGING_CLUB.value, MELODY_CARD_IMAGE.value
                                + melodyCard.getId())
                                .withMethod(HttpMethod.GET)
                                .withExpiration(expiration);
                URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
                melodyCardResponse.updateUrl(url.toString());
            }
            Boolean isLike = likeInfoFindService.findLikeInfo(memberId, melodyCard.getId());
            melodyCardResponse.updateLikeInfo(isLike);

            melodyCardResponses.add(melodyCardResponse);

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
