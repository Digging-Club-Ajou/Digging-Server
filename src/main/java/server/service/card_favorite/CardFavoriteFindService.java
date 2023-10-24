package server.service.card_favorite;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.card_favorite.CardFavorite;
import server.mapper.card_favorite.CardFavoriteMapper;
import server.mapper.card_favorite.dto.CardFavoriteResponse;
import server.repository.card_favorite.CardFavoriteRepository;
import server.service.melody_card.MelodyCardCreateService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardFavoriteFindService {

    private final CardFavoriteRepository cardFavoriteRepository;
    private final MelodyCardCreateService melodyCardCreateService;

    public CardFavoriteFindService(final CardFavoriteRepository cardFavoriteRepository,
                                   final MelodyCardCreateService melodyCardCreateService) {
        this.cardFavoriteRepository = cardFavoriteRepository;
        this.melodyCardCreateService = melodyCardCreateService;
    }

    @Transactional(readOnly = true)
    public List<CardFavoriteResponse> findAllResponses(final long memberId) {
        List<CardFavorite> cardFavorites = cardFavoriteRepository.findAllByMemberId(memberId);

        return cardFavorites.stream()
                .map(cardFavorite -> melodyCardCreateService.getMelodyCard(cardFavorite.getMelodyCardId()))
                .map(CardFavoriteMapper::toCardFavoriteResponse)
                .collect(Collectors.toList());
    }
}
