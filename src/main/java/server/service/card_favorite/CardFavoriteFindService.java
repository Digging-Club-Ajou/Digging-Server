package server.service.card_favorite;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.card_favorite.CardFavorite;
import server.mapper.card_favorite.CardFavoriteMapper;
import server.mapper.card_favorite.dto.CardFavoriteResponse;
import server.mapper.melody_card.MelodyCardMapper;
import server.repository.card_favorite.CardFavoriteRepository;
import server.repository.melody_card.MelodyCardRepository;

import java.util.List;

import static java.util.stream.Collectors.*;

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

        return cardFavorites.stream()
                .map(cardFavorite -> melodyCardRepository.getById(cardFavorite.getMelodyCardId()))
                .map(MelodyCardMapper::toMelodyCardResponse)
                .map(CardFavoriteMapper::toCardFavoriteResponse)
                .collect(toList());
    }
}
