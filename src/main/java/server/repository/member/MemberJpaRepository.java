package server.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.member.persist.Member;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(final String loginId);

    Optional<Member> findByEmail(final String email);
}
