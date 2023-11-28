package server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import server.repository.album.AlbumRepository;
import server.repository.card_favorite.CardFavoriteRepository;
import server.repository.following.FollowingRepository;
import server.repository.melody_card.MelodyCardRepository;
import server.repository.member.MemberRepository;

@Profile("test")
@AcceptanceTest
public abstract class ServiceTest {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected AlbumRepository albumRepository;

    @Autowired
    protected MelodyCardRepository melodyCardRepository;

    @Autowired
    protected FollowingRepository followingRepository;

    @Autowired
    protected CardFavoriteRepository cardFavoriteRepository;
}
