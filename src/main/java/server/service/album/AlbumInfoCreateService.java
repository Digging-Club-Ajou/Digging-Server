package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.member.vo.MemberSession;
import server.mapper.album.AlbumMapper;
import server.repository.album.AlbumRepository;


@Service
public class AlbumInfoCreateService {

    private final AlbumRepository albumRepository;

    public AlbumInfoCreateService(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Transactional
    public Album createProfileInfo(final MemberSession memberSession, final String albumName) {
        Album album = AlbumMapper.toEntity(memberSession, albumName);
        return albumRepository.save(album);
    }
}
