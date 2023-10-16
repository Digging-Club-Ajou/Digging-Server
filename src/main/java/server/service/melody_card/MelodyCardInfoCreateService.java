package server.service.melody_card;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.melody_card.MelodyCard;
import server.mapper.melody_card.MelodyCardMapper;
import server.mapper.melody_card.dto.MelodyCardRequest;
import server.repository.melody_card.MelodyCardRepository;

@Service
public class MelodyCardInfoCreateService {
    private final MelodyCardRepository melodyCardRepository;
    public MelodyCardInfoCreateService(final MelodyCardRepository melodyCardRepository) {
        this.melodyCardRepository = melodyCardRepository;
    }

    @Transactional
    public MelodyCard createMelodyCardInfo(final Album album, final MelodyCardRequest dto) {
        MelodyCard melodyCard = MelodyCardMapper.toEntity(album, dto);
        return melodyCardRepository.save(melodyCard);
    }
}
