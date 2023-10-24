package server.controller.card_favorite;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.service.card_favorite.CardFavoriteCreateService;

@RequestMapping("/api")
@RestController
public class CardFavoriteController {

    private final CardFavoriteCreateService cardFavoriteCreateService;

    public CardFavoriteController(final CardFavoriteCreateService cardFavoriteCreateService) {
        this.cardFavoriteCreateService = cardFavoriteCreateService;
    }

    @PostMapping("/card-favorite/{melodyCardId}")
    public void pushLikes(@Login final MemberSession memberSession,
                          @PathVariable final long melodyCardId) {
        cardFavoriteCreateService.pushLikes(memberSession.id(), melodyCardId);
    }
}
