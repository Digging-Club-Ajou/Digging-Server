package server.service.melody_card;

import org.springframework.stereotype.Service;
import server.repository.melody_card.MelodyCardRepository;


@Service
public class MelodyCardService {
    private MelodyCardRepository melodyCardRepository;

    public MelodyCardService(final MelodyCardRepository melodyCardRepository){
        this.melodyCardRepository = melodyCardRepository;
    }


    public void deleteMelodyCardInfo(final long melodyCardId){
        melodyCardRepository.getById(melodyCardId);
        melodyCardRepository.deleteById(melodyCardId);
    }
}
