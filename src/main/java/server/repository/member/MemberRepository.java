package server.repository.member;

import org.springframework.stereotype.Repository;
import server.domain.member.persist.Member;
import server.global.exception.NotFoundException;

import java.util.List;
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

    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email);
    }

    public Optional<Member> findByNickname(final String nickname) {
        return memberJpaRepository.findByNickname(nickname);
    }

    public long count() {
        return memberJpaRepository.count();
    }



    public List<Member> findByNicknameStartingWith(final String keyword){
        return memberJpaRepository.findByNicknameStartingWith(keyword);
    }

    public List<Member> findByNicknameLike(final String keyword){
        return memberJpaRepository.findByNicknameLike(keyword);
    }

    public List<Member> findAllByNickName(final String nickname){
        return memberJpaRepository.findAllByNickname(nickname);
    }
}
