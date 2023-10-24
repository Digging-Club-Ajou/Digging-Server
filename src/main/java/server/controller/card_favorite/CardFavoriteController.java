package server.controller.card_favorite;

import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.card_favorite.dto.CardFavoriteRequest;
import server.mapper.card_favorite.dto.CardFavoriteResponse;
import server.mapper.card_favorite.dto.CardFavoriteResult;
import server.service.card_favorite.CardFavoriteCreateService;
import server.service.card_favorite.CardFavoriteFindService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CardFavoriteController {

    private final CardFavoriteCreateService cardFavoriteCreateService;
    private final CardFavoriteFindService cardFavoriteFindService;

    public CardFavoriteController(final CardFavoriteCreateService cardFavoriteCreateService,
                                  final CardFavoriteFindService cardFavoriteFindService) {
        this.cardFavoriteCreateService = cardFavoriteCreateService;
        this.cardFavoriteFindService = cardFavoriteFindService;
    }

    @PostMapping("/card-favorites")
    public void changeLikesState(@Login final MemberSession memberSession,
                                 @RequestBody final CardFavoriteRequest cardFavoriteRequest) {
        cardFavoriteCreateService.changeLikesState(memberSession.id(), cardFavoriteRequest);
    }

    @GetMapping("/card-favorites")
    public CardFavoriteResult findAll(@Login final MemberSession memberSession) {
        List<CardFavoriteResponse> cardFavoriteResponses =
                cardFavoriteFindService.findAllResponses(memberSession.id());
        return new CardFavoriteResult(cardFavoriteResponses);
    }
}
