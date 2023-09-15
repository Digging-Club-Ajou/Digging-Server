package server.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.member.persist.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
