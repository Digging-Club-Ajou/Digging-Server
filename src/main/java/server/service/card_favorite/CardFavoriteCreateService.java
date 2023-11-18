package server.service.card_favorite;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.card_favorite.CardFavorite;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.domain.member.vo.MemberSession;
import server.global.exception.BadRequestException;
import server.mapper.card_favorite.CardFavoriteMapper;
import server.mapper.card_favorite.dto.CardFavoriteRequest;
import server.repository.card_favorite.CardFavoriteRepository;
import server.repository.melody_card.MelodyCardRepository;
import server.repository.member.MemberRepository;
import server.service.notification.NotificationCreateService;

import java.util.Optional;

import static server.global.constant.ExceptionMessage.CARD_FAVORITE_NOT_FOUND_EXCEPTION;
import static server.global.constant.NotificationConstant.MELODY_CARD_LIKES_NOTIFICATION;

@Service
public class CardFavoriteCreateService {

    private final CardFavoriteRepository cardFavoriteRepository;
    private final MemberRepository memberRepository;
    private final MelodyCardRepository melodyCardRepository;
    private final NotificationCreateService notificationCreateService;

    public CardFavoriteCreateService(final CardFavoriteRepository cardFavoriteRepository,
                                     final MemberRepository memberRepository,
                                     final MelodyCardRepository melodyCardRepository,
                                     final NotificationCreateService notificationCreateService) {
        this.cardFavoriteRepository = cardFavoriteRepository;
        this.memberRepository = memberRepository;
        this.melodyCardRepository = melodyCardRepository;
        this.notificationCreateService = notificationCreateService;
    }

    @Transactional
    public void saveFavorite(final long memberId, final long melodyCardId) {
        Optional<CardFavorite> optionalCardFavorite =
                cardFavoriteRepository.findByMemberIdAndMelodyCardId(memberId, melodyCardId);

        if (optionalCardFavorite.isEmpty()) {
            CardFavorite cardFavorite = CardFavoriteMapper.toEntity(memberId, melodyCardId);
            cardFavoriteRepository.save(cardFavorite);
            notification(memberId, melodyCardId);
        }
    }

    private void notification(final long memberId, final long melodyCardId) {
        Member member = memberRepository.getById(memberId);
        MelodyCard melodyCard = melodyCardRepository.getById(melodyCardId);
        String notificationMessage = member.getNickname() + MELODY_CARD_LIKES_NOTIFICATION;
        notificationCreateService.saveNotificationMessage(melodyCard.getMemberId(), notificationMessage);
    }

    @Transactional
    public void deleteFavorite(final MemberSession memberSession, final Long melodyCardId) {

        Optional<CardFavorite> cardFavorite =
                cardFavoriteRepository.findByMemberIdAndMelodyCardId(memberSession.id(),melodyCardId);

        if(cardFavorite.isPresent()){
            cardFavoriteRepository.deleteByCardFavorite(cardFavorite.get());
        }else{
            throw new BadRequestException(CARD_FAVORITE_NOT_FOUND_EXCEPTION.message);
        }
    }
}
