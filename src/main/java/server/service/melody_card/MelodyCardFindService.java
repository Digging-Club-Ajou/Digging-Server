package server.service.melody_card;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.melody_card.MelodyCard;
import server.domain.member.vo.MemberSession;
import server.global.exception.BadRequestException;
import server.mapper.melody_card.MelodyCardMapper;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.repository.album.AlbumRepository;
import server.repository.melody_card.MelodyCardRepository;

import java.util.List;

import static server.global.constant.ExceptionMessage.MELODY_CARD_LIMIT;

@Service
public class MelodyCardFindService {

    private final MelodyCardRepository melodyCardRepository;
    private final AlbumRepository albumRepository;

    public MelodyCardFindService(final MelodyCardRepository melodyCardRepository,
                                 final AlbumRepository albumRepository) {
        this.melodyCardRepository = melodyCardRepository;
        this.albumRepository = albumRepository;
    }

    @Transactional(readOnly = true)
    public List<MelodyCard> findMelodyCards(final long memberId) {
        Album album = albumRepository.getByMemberId(memberId);
        List<MelodyCard> melodyCards = melodyCardRepository.findAllByAlbumId(album.getId());

        if(melodyCards.size() > 10){
            throw new BadRequestException(MELODY_CARD_LIMIT.message);
        }

        return melodyCards;
    }

    @Transactional(readOnly = true)
    public MelodyCardResponse findMelodyCardResponse(final MemberSession memberSession, final long melodyCardId) {
        MelodyCard melodyCard = melodyCardRepository.getById(melodyCardId);
        return MelodyCardMapper.toMelodyCardResponse(memberSession, melodyCard);
    }
}