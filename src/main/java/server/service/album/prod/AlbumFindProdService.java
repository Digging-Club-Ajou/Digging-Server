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
import server.service.melody_card.MelodyCardFindService;

import java.net.URL;
import java.util.Date;
import java.util.List;

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
    private final MelodyCardFindService melodyCardFindService;

    public AlbumFindProdService(final AmazonS3 amazonS3Client,
                                final AlbumValidationService albumValidationService,
                                final AlbumRepository albumRepository,
                                final MelodyCardFindService melodyCardFindService) {
        this.amazonS3Client = amazonS3Client;
        this.albumValidationService = albumValidationService;
        this.albumRepository = albumRepository;
        this.melodyCardFindService = melodyCardFindService;
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
            List<String> artistNames = melodyCardFindService.findArtistNamesByAlbumId(albumId);
            return toAlbumResponse(album, imageUrl, artistNames);
        }

        throw new NotFoundException(ALBUM_NOT_FOUND_EXCEPTION.message);
    }

    public AlbumResponse getLoginMemberAlbumResponse(final long memberId) {
        Album album = albumRepository.getByMemberId(memberId);
        return getAlbumResponse(album.getId());
    }

    public String getAlbumUrl(final long memberId) {
        Album album = albumRepository.getByMemberId(memberId);
        if (albumValidationService.validateExistByAlbumId(album.getId())) {
            Date expiration = new Date();
            long expTimeMillis = expiration.getTime() + ONE_HOUR.value;
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(
                            DIGGING_CLUB.value,
                            ALBUM_IMAGE.value + album.getId())
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            String imageUrl = url.toString();

            return imageUrl;
        }

        throw new NotFoundException(ALBUM_NOT_FOUND_EXCEPTION.message);
    }
}
