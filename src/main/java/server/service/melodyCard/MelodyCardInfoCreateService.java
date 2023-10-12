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
    public void createMelodyCardInfo(final Album album, final MelodyCardRequest dto) {
        MelodyCard melodyCard = MelodyCardMapper.toEntity(album, dto);
        melodyCardRepository.save(melodyCard);
    }
}
