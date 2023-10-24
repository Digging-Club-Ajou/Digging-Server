package server.service.card_favorite;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.card_favorite.CardFavorite;
import server.repository.card_favorite.CardFavoriteRepository;

@Service
public class LikeInfoFindService {

    private final CardFavoriteRepository cardFavoriteRepository;

    public LikeInfoFindService(final CardFavoriteRepository cardFavoriteRepository) {
        this.cardFavoriteRepository = cardFavoriteRepository;
    }

    @Transactional(readOnly = true)
    public Boolean findLikeInfo(final long memberId, final long melodyCardId) {
        CardFavorite cardFavorite = cardFavoriteRepository.getByMemberIdAndMelodyCardId(memberId, melodyCardId);
        return cardFavorite.getIsLike();
    }
}
