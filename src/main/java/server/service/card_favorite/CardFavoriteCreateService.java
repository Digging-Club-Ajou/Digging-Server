package server.service.card_favorite;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.card_favorite.CardFavorite;
import server.mapper.card_favorite.CardFavoriteMapper;
import server.repository.card_favorite.CardFavoriteRepository;

@Service
public class CardFavoriteCreateService {

    private final CardFavoriteRepository cardFavoriteRepository;

    public CardFavoriteCreateService(final CardFavoriteRepository cardFavoriteRepository) {
        this.cardFavoriteRepository = cardFavoriteRepository;
    }

    @Transactional
    public void pushLikes(final long memberId, final long melodyCardId) {
        CardFavorite cardFavorite = CardFavoriteMapper.toEntity(memberId, melodyCardId);
        cardFavoriteRepository.save(cardFavorite);
    }
}
