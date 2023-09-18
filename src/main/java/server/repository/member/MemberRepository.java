package server.repository.member;

import org.springframework.stereotype.Repository;
import server.domain.member.persist.Member;
import server.global.exception.NotFoundException;

import java.util.Optional;

import static server.global.constant.ExceptionMessage.*;

@Repository
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepository(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    public Member getById(final long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND_EXCEPTION.message));
    }

    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    public long count() {
        return memberJpaRepository.count();
    }
}
