package server.service.album;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.member.vo.MemberSession;
import server.global.exception.BadRequestException;
import server.mapper.album.AlbumMapper;
import server.repository.album.AlbumRepository;

import static server.global.constant.ExceptionMessage.*;

@Service
public class AlbumInfoCreateService {

    private final AlbumRepository albumRepository;

    public AlbumInfoCreateService(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Transactional
    public void createProfileInfo(final MemberSession memberSession, final String albumName) {
        if (albumRepository.existsByAlbumName(albumName)) {
            throw new BadRequestException(ALBUM_NAME_ALREADY_EXIST_EXCEPTION.message);
        }

        Album album = AlbumMapper.toEntity(memberSession, albumName);
        albumRepository.save(album);
    }
}
