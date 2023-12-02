package server.dummy;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.domain.music_recommentdation.MusicRecommendation;
import server.dummy.dto.*;
import server.mapper.spotify.SpotifySearchDto;
import server.repository.album.AlbumRepository;
import server.repository.melody_card.MelodyCardRepository;
import server.repository.member.MemberRepository;
import server.repository.music_recommendation.MusicRecommendationRepository;
import server.service.spotify.SpotifySearchMusicService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;

import static server.global.constant.TextConstant.*;

@Service
public class DummyService {

    private final MemberRepository memberRepository;
    private final AlbumRepository albumRepository;
    private final MelodyCardRepository melodyCardRepository;
    private final MusicRecommendationRepository musicRecommendationRepository;
    private final AmazonS3Client amazonS3Client;
    private final SpotifySearchMusicService spotifySearchMusicService;

    public DummyService(final MemberRepository memberRepository,
                        final AlbumRepository albumRepository,
                        final MelodyCardRepository melodyCardRepository,
                        final MusicRecommendationRepository musicRecommendationRepository,
                        final AmazonS3Client amazonS3Client,
                        final SpotifySearchMusicService spotifySearchMusicService) {
        this.memberRepository = memberRepository;
        this.albumRepository = albumRepository;
        this.melodyCardRepository = melodyCardRepository;
        this.musicRecommendationRepository = musicRecommendationRepository;
        this.amazonS3Client = amazonS3Client;
        this.spotifySearchMusicService = spotifySearchMusicService;
    }

    @Transactional
    public void dummySave(final DummyDto dummyDto) {
        for (RequestDto requestDto : dummyDto.requestDtos()) {
            Member member = saveMember(requestDto);

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
                        .address(melodyCardDto.address())
                        .cardDescription(melodyCardDto.cardDescription())
                        .color(melodyCardDto.color())
                        .isImageUrl(true)
                        .albumCoverImageUrl(spotifySearchDto.imageUrl())
                        .build();

                melodyCardRepository.save(melodyCard);

                byte[] melodyCardDecodedBytes = Base64.getDecoder().decode(albumDto.albumImage());
                ByteBuffer melodyCardByteBuffer = ByteBuffer.wrap(melodyCardDecodedBytes);

                ObjectMetadata melodyCardMetadata = new ObjectMetadata();
                melodyCardMetadata.setContentLength(melodyCardByteBuffer.array().length);

                InputStream melodyCardTargetStream = new ByteArrayInputStream(melodyCardByteBuffer.array());
                PutObjectRequest melodyCardPutObjectRequest =
                        new PutObjectRequest(DIGGING_CLUB.value, MELODY_CARD_IMAGE.value + melodyCard.getId(),
                                melodyCardTargetStream, melodyCardMetadata);

                amazonS3Client.putObject(melodyCardPutObjectRequest);

                MusicRecommendation musicRecommendation = MusicRecommendation.builder()
                        .artistName(melodyCardDto.artistName())
                        .build();

                musicRecommendationRepository.save(musicRecommendation);
            }
        }
    }

    private Member saveMember(final RequestDto requestDto) {
        MemberDto memberDto = requestDto.memberDto();
        Member member = Member.builder()
                .nickname(memberDto.nickname())
                .build();

        memberRepository.save(member);
        return member;
    }
}
