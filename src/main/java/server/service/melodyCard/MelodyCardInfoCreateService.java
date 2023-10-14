package server.service.melodyCard;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.melodyCard.MelodyCard;
import server.mapper.melodyCard.MelodyCardMapper;
import server.mapper.melodyCard.dto.MelodyCardRequest;
import server.repository.melodyCard.MelodyCardRepository;

@Service
public class MelodyCardInfoCreateService {
    private final MelodyCardRepository melodyCardRepository;
    public MelodyCardInfoCreateService(final MelodyCardRepository melodyCardRepository) {
        this.melodyCardRepository = melodyCardRepository;
    }

    @Transactional
    public MelodyCard createMelodyCardInfo(final long albumId, final MelodyCardRequest dto) {
        MelodyCard melodyCard = MelodyCardMapper.toEntity(albumId, dto);
        return melodyCardRepository.save(melodyCard);
    }
}
