package server.service.archive;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.mapper.archive.dto.ArchiveRequest;
import server.mapper.artist.ArtistMapper;
import server.mapper.genre.GenreMapper;
import server.mapper.genre.dto.GenreRequest;
import server.mapper.member.dto.NicknameRequest;
import server.repository.artist.ArtistRepository;
import server.repository.genre.GenreRepository;
import server.repository.member.MemberRepository;

import java.util.List;

@Service
public class ArchiveCreateService {

    private final MemberRepository memberRepository;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;

    public ArchiveCreateService(final MemberRepository memberRepository,
                                final GenreRepository genreRepository,
                                final ArtistRepository artistRepository) {
        this.memberRepository = memberRepository;
        this.genreRepository = genreRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional
    public void createArchive(final long memberId, final ArchiveRequest dto) {
        NicknameRequest nicknameRequest = dto.nicknameRequest();
        nicknameRequest.validateRegex();

        Member member = memberRepository.getById(memberId);
        member.createNickname(nicknameRequest.nickname());

        GenreRequest genreRequest = dto.genreRequest();
        genreRepository.save(GenreMapper.toEntity(memberId, genreRequest));

        List<String> artistNames = dto.artists();
        artistNames.stream()
                .map(
                        artistRequest -> ArtistMapper.toEntity(memberId, artistRequest)
                )
                .forEach(artistRepository::save);
    }
}
