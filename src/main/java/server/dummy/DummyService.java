package server.dummy;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import server.domain.album.Album;
import server.domain.genre.Genre;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.domain.music_recommentdation.MusicRecommendation;
import server.dummy.dto.*;
import server.mapper.spotify.SpotifySearchDto;
import server.repository.album.AlbumRepository;
import server.repository.genre.GenreRepository;
import server.repository.melody_card.MelodyCardRepository;
import server.repository.member.MemberRepository;
import server.repository.music_recommendation.MusicRecommendationRepository;
import server.service.spotify.SpotifySearchMusicService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import static server.global.constant.TextConstant.*;

@Service
public class DummyService {

    private final MemberRepository memberRepository;
    private final AlbumRepository albumRepository;
    private final MelodyCardRepository melodyCardRepository;
    private final MusicRecommendationRepository musicRecommendationRepository;
    private final GenreRepository genreRepository;
    private final AmazonS3Client amazonS3Client;
    private final SpotifySearchMusicService spotifySearchMusicService;
    private final RestTemplate restTemplate;

    public DummyService(final MemberRepository memberRepository,
                        final AlbumRepository albumRepository,
                        final MelodyCardRepository melodyCardRepository,
                        final MusicRecommendationRepository musicRecommendationRepository,
                        final GenreRepository genreRepository, final AmazonS3Client amazonS3Client,
                        final SpotifySearchMusicService spotifySearchMusicService,
                        final RestTemplate restTemplate) {
        this.memberRepository = memberRepository;
        this.albumRepository = albumRepository;
        this.melodyCardRepository = melodyCardRepository;
        this.musicRecommendationRepository = musicRecommendationRepository;
        this.genreRepository = genreRepository;
        this.amazonS3Client = amazonS3Client;
        this.spotifySearchMusicService = spotifySearchMusicService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void dummySave(final DummyDto dummyDto) {
        for (RequestDto requestDto : dummyDto.requestDtos()) {
            Member member = saveMember();

            AlbumDto albumDto = requestDto.albumDto();
            Album album = Album.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .albumName(albumDto.albumName())
                    .build();

            albumRepository.save(album);

            byte[] albumDecodedBytes = Base64.getDecoder().decode(albumDto.albumImage());
            ByteBuffer albumByteBuffer = ByteBuffer.wrap(albumDecodedBytes);

            ObjectMetadata albumMetadata = new ObjectMetadata();
            albumMetadata.setContentLength(albumByteBuffer.array().length);

            InputStream albumTargetStream = new ByteArrayInputStream(albumByteBuffer.array());
            PutObjectRequest albumPutObjectRequest =
                    new PutObjectRequest(DIGGING_CLUB.value, ALBUM_IMAGE.value + album.getId(),
                            albumTargetStream, albumMetadata);

            amazonS3Client.putObject(albumPutObjectRequest);

            List<MelodyCardDto> melodyCardDtos = requestDto.melodyCardDtos();
            for (MelodyCardDto melodyCardDto : melodyCardDtos) {
                List<SpotifySearchDto> spotifySearchDtos = spotifySearchMusicService.searchTracks(
                        melodyCardDto.artistName() + " " + melodyCardDto.songTitle(), 1);
                SpotifySearchDto spotifySearchDto = spotifySearchDtos.get(0);

                MelodyCard melodyCard = MelodyCard.builder()
                        .albumId(album.getId())
                        .memberId(member.getId())
                        .nickname(member.getNickname())
                        .artistName(spotifySearchDto.artistName())
                        .songTitle(spotifySearchDto.songTitle())
                        .previewUrl(spotifySearchDto.previewUrl())
                        .isImageUrl(true)
                        .albumCoverImageUrl(spotifySearchDto.imageUrl())
                        .build();

                melodyCardRepository.save(melodyCard);

                String url = spotifySearchDto.imageUrl();

                ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url, byte[].class);
                byte[] melodyCardBytes = responseEntity.getBody();

                assert melodyCardBytes != null;
                ByteBuffer melodyCardByteBuffer = ByteBuffer.wrap(melodyCardBytes);

                ObjectMetadata melodyCardMetadata = new ObjectMetadata();
                melodyCardMetadata.setContentLength(melodyCardByteBuffer.array().length);

                InputStream melodyCardTargetStream = new ByteArrayInputStream(melodyCardByteBuffer.array());
                PutObjectRequest melodyCardPutObjectRequest =
                        new PutObjectRequest(DIGGING_CLUB.value, MELODY_CARD_IMAGE.value + melodyCard.getId(),
                                melodyCardTargetStream, melodyCardMetadata);

                amazonS3Client.putObject(melodyCardPutObjectRequest);

                MusicRecommendation musicRecommendation = MusicRecommendation.builder()
                        .memberId(member.getId())
                        .artistName(spotifySearchDto.artistName())
                        .build();

                musicRecommendationRepository.save(musicRecommendation);
            }

            Genre genre = getGenre(member.getId());
            genreRepository.save(genre);
        }
    }

    private Member saveMember() {
        Member member = Member.builder()
                .nickname("admin")
                .build();

        memberRepository.save(member);
        return member;
    }
    
    private Genre getGenre(final long memberId) {
        Genre genre1 = Genre.builder()
                .memberId(memberId)
                .ballade(true)
                .dance(true)
                .rockMetal(true)
                .rbAndSoul(false)
                .rapHiphop(false)
                .folkBlues(false)
                .indie(true)
                .pop(false)
                .ostAndMusical(true)
                .build();

        Genre genre2 = Genre.builder()
                .memberId(memberId)
                .ballade(false)
                .dance(true)
                .rockMetal(false)
                .rbAndSoul(false)
                .rapHiphop(false)
                .folkBlues(true)
                .indie(true)
                .pop(true)
                .ostAndMusical(true)
                .build();

        Genre genre3 = Genre.builder()
                .memberId(memberId)
                .ballade(true)
                .dance(true)
                .rockMetal(false)
                .rbAndSoul(false)
                .rapHiphop(false)
                .folkBlues(false)
                .indie(true)
                .pop(true)
                .ostAndMusical(true)
                .build();

        Genre genre4 = Genre.builder()
                .memberId(memberId)
                .ballade(false)
                .dance(false)
                .rockMetal(true)
                .rbAndSoul(true)
                .rapHiphop(true)
                .folkBlues(true)
                .indie(false)
                .pop(false)
                .ostAndMusical(true)
                .build();

        Genre genre5 = Genre.builder()
                .memberId(memberId)
                .ballade(false)
                .dance(true)
                .rockMetal(false)
                .rbAndSoul(false)
                .rapHiphop(true)
                .folkBlues(true)
                .indie(false)
                .pop(true)
                .ostAndMusical(true)
                .build();

        List<Genre> genres = new ArrayList<>();
        genres.add(genre1);
        genres.add(genre2);
        genres.add(genre3);
        genres.add(genre4);
        genres.add(genre5);
        Random random = new Random();
        int randomNumber = random.nextInt(5);

        return genres.get(randomNumber);
    }
}
