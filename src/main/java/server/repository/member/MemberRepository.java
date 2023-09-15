package server.repository.member;

import org.springframework.stereotype.Repository;
import server.domain.member.persist.Member;

@Repository
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepository(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    public void save(final Member member) {
        memberJpaRepository.save(member);
    }
}
