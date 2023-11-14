package server.controller.card_favorite;

import org.springframework.web.bind.annotation.*;
import server.domain.member.vo.MemberSession;
import server.global.annotation.Login;
import server.mapper.card_favorite.dto.CardFavoriteRequest;
import server.mapper.card_favorite.dto.CardFavoriteResponse;
import server.mapper.card_favorite.dto.CardFavoriteResult;
import server.mapper.card_favorite.dto.LikeInfoResponse;
import server.service.card_favorite.CardFavoriteCreateService;
import server.service.card_favorite.CardFavoriteFindService;
import server.service.card_favorite.LikeInfoFindService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CardFavoriteController {

    private final CardFavoriteCreateService cardFavoriteCreateService;
    private final CardFavoriteFindService cardFavoriteFindService;
    private final LikeInfoFindService likeInfoFindService;

    public CardFavoriteController(final CardFavoriteCreateService cardFavoriteCreateService,
                                  final CardFavoriteFindService cardFavoriteFindService,
                                  final LikeInfoFindService likeInfoFindService) {
        this.cardFavoriteCreateService = cardFavoriteCreateService;
        this.cardFavoriteFindService = cardFavoriteFindService;
        this.likeInfoFindService = likeInfoFindService;
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

    @GetMapping("/card-favorites/members/{memberId}")
    public CardFavoriteResult findAllByMemberId(@PathVariable final long memberId) {
        List<CardFavoriteResponse> cardFavoriteResponses =
                cardFavoriteFindService.findAllResponses(memberId);
        return new CardFavoriteResult(cardFavoriteResponses);
    }

    @GetMapping("/card-favorites/likes/{melodyCardId}")
    public LikeInfoResponse findLikeInfo(@Login final MemberSession memberSession,
                                         @PathVariable final long melodyCardId) {
        Boolean likeInfo = likeInfoFindService.findLikeInfo(memberSession.id(), melodyCardId);
        return new LikeInfoResponse(likeInfo);
    }


    @DeleteMapping("/card-favorites/likes/{melodyCardId}")
    public void deleteByMelodyCardIdAndMemberId(@Login final MemberSession memberSession,@PathVariable final long melodyCardId) {
        cardFavoriteCreateService.deleteFavorite(memberSession,melodyCardId);
    }
}
