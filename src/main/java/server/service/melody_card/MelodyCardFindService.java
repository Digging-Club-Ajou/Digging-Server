package server.service.melody_card;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.global.exception.BadRequestException;
import server.mapper.melody_card.MelodyCardMapper;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.repository.album.AlbumRepository;
import server.repository.melody_card.MelodyCardRepository;
import server.repository.member.MemberRepository;

import java.util.List;

import static server.global.constant.ExceptionMessage.MELODY_CARD_LIMIT;

@Service
public class MelodyCardFindService {

    private final MelodyCardRepository melodyCardRepository;

    public MelodyCardFindService(final MelodyCardRepository melodyCardRepository) {
        this.melodyCardRepository = melodyCardRepository;
    }

    @Transactional(readOnly = true)
    public List<MelodyCard> findMelodyCards(final long memberId) {
        List<MelodyCard> melodyCards = melodyCardRepository.findAllByMemberId(memberId);

        if(melodyCards.size() > 10){
            throw new BadRequestException(MELODY_CARD_LIMIT.message);
        }

        return melodyCards;
    }

    @Transactional(readOnly = true)
    public List<MelodyCard> findMelodyCardsByAlbumId(final long albumId) {
        List<MelodyCard> melodyCards = melodyCardRepository.findAllByAlbumId(albumId);

        if(melodyCards.size() > 10){
            throw new BadRequestException(MELODY_CARD_LIMIT.message);
        }

        return melodyCards;
    }

    @Transactional(readOnly = true)
    public List<String> findArtistNamesByAlbumId(final long albumId) {
        return melodyCardRepository.findAllArtistName(albumId);
    }

    @Transactional(readOnly = true)
    public MelodyCardResponse findMelodyCardResponse(final long melodyCardId) {
        MelodyCard melodyCard = melodyCardRepository.getById(melodyCardId);
        return MelodyCardMapper.toMelodyCardResponse(melodyCard);
    }
}
