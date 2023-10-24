package server.service.card_favorite;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.card_favorite.CardFavorite;
import server.mapper.card_favorite.CardFavoriteMapper;
import server.mapper.card_favorite.dto.CardFavoriteRequest;
import server.repository.card_favorite.CardFavoriteRepository;

import java.util.Optional;

@Service
public class CardFavoriteCreateService {

    private final CardFavoriteRepository cardFavoriteRepository;

    public CardFavoriteCreateService(final CardFavoriteRepository cardFavoriteRepository) {
        this.cardFavoriteRepository = cardFavoriteRepository;
    }

    @Transactional
    public void changeLikesState(final long memberId, final CardFavoriteRequest dto) {
        Optional<CardFavorite> optionalCardFavorite =
                cardFavoriteRepository.findByMemberIdAndMelodyCardId(memberId, dto.melodyCardId());

        if (optionalCardFavorite.isPresent()) {
            CardFavorite cardFavorite = optionalCardFavorite.get();
            cardFavorite.updateState(dto.isLikes());
        } else {
            CardFavorite cardFavorite = CardFavoriteMapper.toEntity(memberId, dto);
            cardFavoriteRepository.save(cardFavorite);
        }
    }
}
