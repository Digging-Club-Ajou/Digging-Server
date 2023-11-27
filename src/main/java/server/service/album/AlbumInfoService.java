package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.mapper.album.AlbumMapper;
import server.repository.album.AlbumRepository;
import server.repository.member.MemberRepository;


@Service
public class AlbumInfoService {

    private final AlbumRepository albumRepository;
    private final MemberRepository memberRepository;

    public AlbumInfoService(final AlbumRepository albumRepository, final MemberRepository memberRepository) {
        this.albumRepository = albumRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Album createProfileInfo(final long memberId, final String albumName) {
        Member member = memberRepository.getById(memberId);
        Album album = AlbumMapper.toEntity(member, albumName);
        return albumRepository.save(album);
    }

    @Transactional
    public Album updateProfileInfo(final long memberId, final String albumName){
        Member member = memberRepository.getById(memberId);
        Album album = albumRepository.getByMemberId(member.getId());

        if(albumName != null){
            album.updateAlbumName(albumName);
        }

        return album;
    }
}
