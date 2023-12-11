package server.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.member.persist.Member;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(final String nickname);

    Optional<Member> findByEmail(final String email);

    Optional<Member> findByKakaoId(final long kakaoId);

    List<Member> findAllByNickname(final String nickname);

    List<Member> findByNicknameStartingWith(final String nickname);

    List<Member> findByNicknameLike(final String nickname);
}
