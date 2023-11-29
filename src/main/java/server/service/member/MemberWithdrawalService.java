package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.album.Album;
import server.domain.card_favorite.CardFavorite;
import server.domain.following.FollowingInfo;
import server.domain.melody_card.MelodyCard;
import server.domain.member.persist.Member;
import server.domain.withdrawal.persist.Withdrawal;
import server.mapper.withdrawal.WithdrawalMapper;
import server.mapper.withdrawal.dto.MemberWithdrawalRequest;
import server.repository.album.AlbumRepository;
import server.repository.card_favorite.CardFavoriteRepository;
import server.repository.following.FollowingRepository;
import server.repository.melody_card.MelodyCardRepository;
import server.repository.member.MemberRepository;
import server.repository.withdrawal.WithdrawalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberWithdrawalService {

    private final MemberRepository memberRepository;
    private final AlbumRepository albumRepository;
    private final MelodyCardRepository melodyCardRepository;
    private final FollowingRepository followingRepository;
    private final CardFavoriteRepository cardFavoriteRepository;
    private final WithdrawalRepository withdrawalRepository;

    public MemberWithdrawalService(final MemberRepository memberRepository,
                                   final AlbumRepository albumRepository,
                                   final MelodyCardRepository melodyCardRepository,
                                   final FollowingRepository followingRepository,
                                   final CardFavoriteRepository cardFavoriteRepository,
                                   final WithdrawalRepository withdrawalRepository) {
        this.memberRepository = memberRepository;
        this.albumRepository = albumRepository;
        this.melodyCardRepository = melodyCardRepository;
        this.followingRepository = followingRepository;
        this.cardFavoriteRepository = cardFavoriteRepository;
        this.withdrawalRepository = withdrawalRepository;
    }

    @Transactional
    public void withdrawal(final long memberId, final MemberWithdrawalRequest dto) {
        Member member = memberRepository.getById(memberId);
        deleteAllMelodyCard(memberId);
        deleteAlbum(memberId);
        deleteFollowing(memberId);
        deleteCardFavorite(memberId);
        memberRepository.delete(member);

        Withdrawal withdrawal = WithdrawalMapper.toEntity(memberId, dto);
        withdrawalRepository.save(withdrawal);
    }

    private void deleteAllMelodyCard(final long memberId) {
        List<MelodyCard> melodyCards = melodyCardRepository.findAllByMemberId(memberId);
        melodyCards.forEach(melodyCardRepository::delete);
    }

    private void deleteAlbum(final long memberId) {
        Optional<Album> optionalAlbum = albumRepository.findByMemberId(memberId);
        if (optionalAlbum.isPresent()) {
            Album album = optionalAlbum.get();
            albumRepository.delete(album);
        }
    }

    private void deleteFollowing(final long memberId){
        List<FollowingInfo> followingInfos = followingRepository.findAllByFollowingId(memberId);
        List<FollowingInfo> followerInfos = followingRepository.findAllByFollowedId(memberId);
        followingInfos.forEach(followingRepository::delete);
        followerInfos.forEach(followingRepository::delete);
    }

    private void deleteCardFavorite(final long memberId) {
        List<CardFavorite> cardFavorites = cardFavoriteRepository.findAllByMemberId(memberId);
        for (CardFavorite cardFavorite : cardFavorites) {
            cardFavoriteRepository.delete(cardFavorite);
        }
    }
}
