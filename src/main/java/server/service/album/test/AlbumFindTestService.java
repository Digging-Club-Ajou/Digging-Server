package server.service.album.test;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import server.domain.album.Album;
import server.global.exception.NotFoundException;
import server.mapper.album.dto.AlbumResponse;
import server.repository.album.AlbumRepository;
import server.service.album.AlbumFindService;
import server.service.album.AlbumValidationService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static server.global.constant.ExceptionMessage.ALBUM_NOT_FOUND_EXCEPTION;
import static server.global.constant.TestConstant.TEST_URL;

// Test Double
@Profile("test")
@Service
public class AlbumFindTestService implements AlbumFindService {

    private final AlbumValidationService albumValidationService;
    private final AlbumRepository albumRepository;

    public AlbumFindTestService(final AlbumValidationService albumValidationService,
                                final AlbumRepository albumRepository) {
        this.albumValidationService = albumValidationService;
        this.albumRepository = albumRepository;
    }

    @Override
    public Optional<Album> findByMemberId(final long memberId) {
        return albumRepository.findByMemberId(memberId);
    }

    @Override
    public AlbumResponse getAlbumResponse(final long albumId) {
        List<String> artistNames = new ArrayList<>();
        artistNames.add("artist1");
        artistNames.add("artist2");
        artistNames.add("artist3");

        Album album = Album.builder()
                .memberId(albumId)
                .nickname("닉네임")
                .albumName("앨범 이름" + albumId)
                .build();

        if (albumId == 9999L) {
            throw new NotFoundException(ALBUM_NOT_FOUND_EXCEPTION.message);
        }

        return AlbumResponse.toAlbumResponseTest(album, TEST_URL.value, artistNames);
    }

    @Override
    public AlbumResponse getLoginMemberAlbumResponse(final long memberId) {
        List<String> artistNames = new ArrayList<>();
        artistNames.add("artist1");
        artistNames.add("artist2");
        artistNames.add("artist3");

        Album album = Album.builder()
                .memberId(memberId)
                .nickname("닉네임")
                .albumName("앨범 이름" + memberId)
                .build();

        if (memberId == 9999L) {
            throw new NotFoundException(ALBUM_NOT_FOUND_EXCEPTION.message);
        }

        return AlbumResponse.toAlbumResponseTest(album, TEST_URL.value, artistNames);
    }
}
