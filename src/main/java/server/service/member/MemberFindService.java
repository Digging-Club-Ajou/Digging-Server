package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.member.vo.MemberSession;
import server.mapper.member.dto.MemberResponse;
import server.repository.album.AlbumRepository;

@Service
public class MemberFindService {

    private final AlbumRepository albumRepository;

    public MemberFindService(final AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(final MemberSession memberSession) {
        Album album = albumRepository.getByMemberId(memberSession.id());
        return new MemberResponse(memberSession.id(), album.getId());
    }
}
