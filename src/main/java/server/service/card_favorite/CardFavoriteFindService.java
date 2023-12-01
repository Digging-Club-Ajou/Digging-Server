package server.service.card_favorite;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.card_favorite.CardFavorite;
import server.domain.melody_card.MelodyCard;
import server.global.exception.NotFoundException;
import server.mapper.card_favorite.CardFavoriteMapper;
import server.mapper.card_favorite.dto.CardFavoriteResponse;
import server.mapper.melody_card.MelodyCardMapper;
import server.mapper.melody_card.dto.MelodyCardResponse;
import server.repository.card_favorite.CardFavoriteRepository;
import server.repository.melody_card.MelodyCardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static server.global.constant.ExceptionMessage.MELODY_CARD_NOT_FOUND;


@Service
public class CardFavoriteFindService {

    private final CardFavoriteRepository cardFavoriteRepository;
    private final MelodyCardRepository melodyCardRepository;

    public CardFavoriteFindService(final CardFavoriteRepository cardFavoriteRepository,
                                   final MelodyCardRepository melodyCardRepository) {
        this.cardFavoriteRepository = cardFavoriteRepository;
        this.melodyCardRepository = melodyCardRepository;
    }

    @Transactional(readOnly = true)
    public List<CardFavoriteResponse> findAllResponses(final long memberId) {
        List<CardFavorite> cardFavorites = cardFavoriteRepository.findAllByMemberId(memberId);

        if(cardFavorites.isEmpty()){
            throw new NotFoundException(MELODY_CARD_NOT_FOUND.message);
        }

        List<CardFavoriteResponse> CardFavoriteResponses = new ArrayList<>();
        for (CardFavorite cardFavorite : cardFavorites) {
            Optional<MelodyCard> optionalMelodyCard = melodyCardRepository.findById(cardFavorite.getMelodyCardId());

            if (optionalMelodyCard.isPresent()) {
                MelodyCard melodyCard = optionalMelodyCard.get();
                MelodyCardResponse melodyCardResponse = MelodyCardMapper.toMelodyCardResponse(melodyCard);
                CardFavoriteResponse cardFavoriteResponse =
                        CardFavoriteMapper.toCardFavoriteResponse(melodyCardResponse);
                CardFavoriteResponses.add(cardFavoriteResponse);
            }
        }
        return CardFavoriteResponses;
    }
}
