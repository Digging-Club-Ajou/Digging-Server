package server.service.card_favorite;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.card_favorite.CardFavorite;
import server.domain.member.vo.MemberSession;
import server.global.exception.BadRequestException;
import server.repository.card_favorite.CardFavoriteRepository;

import java.util.Optional;

import static server.global.constant.ExceptionMessage.CARD_FAVORITE_NOT_FOUND_EXCEPTION;

@Service
public class CardFavoriteDeleteService {

    private final CardFavoriteRepository cardFavoriteRepository;

    public CardFavoriteDeleteService(final CardFavoriteRepository cardFavoriteRepository) {
        this.cardFavoriteRepository = cardFavoriteRepository;
    }

    @Transactional
    public void deleteFavorite(final long memberId, final long melodyCardId) {
        Optional<CardFavorite> cardFavorite =
                cardFavoriteRepository.findByMemberIdAndMelodyCardId(memberId,melodyCardId);

        if (cardFavorite.isPresent()) {
            cardFavoriteRepository.deleteByCardFavorite(cardFavorite.get());
        } else {
            throw new BadRequestException(CARD_FAVORITE_NOT_FOUND_EXCEPTION.message);
        }
    }
}
