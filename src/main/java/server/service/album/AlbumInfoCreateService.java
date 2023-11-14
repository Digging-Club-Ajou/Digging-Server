package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.mapper.album.AlbumMapper;
import server.repository.album.AlbumRepository;
import server.repository.member.MemberRepository;


@Service
public class AlbumInfoCreateService {

    private final AlbumRepository albumRepository;
    private final MemberRepository memberRepository;

    public AlbumInfoCreateService(final AlbumRepository albumRepository, final MemberRepository memberRepository) {
        this.albumRepository = albumRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Album createProfileInfo(final MemberSession memberSession, final String albumName) {
        Member member = memberRepository.getById(memberSession.id());
        Album album = AlbumMapper.toEntity(member, albumName);
        return albumRepository.save(album);
    }

    @Transactional
    public Album updateProfileInfo(final MemberSession memberSession, final String albumName){
        Member member = memberRepository.getById(memberSession.id());
        Album album = albumRepository.getByMemberId(member.getId());

        if(albumName!= null){
            album.updateAlbumName(albumName);
        }

        return album;
    }
}
