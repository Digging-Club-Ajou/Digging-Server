package server.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.member.persist.Member;
import server.domain.withdrawal.persist.Withdrawal;
import server.domain.withdrawal.vo.WithdrawalReason;
import server.mapper.withdrawal.WithdrawalMapper;
import server.mapper.withdrawal.dto.MemberWithdrawalRequest;
import server.repository.member.MemberRepository;
import server.repository.withdrawal.WithdrawalRepository;

@Service
public class MemberWithdrawalService {

    private final MemberRepository memberRepository;
    private final WithdrawalRepository withdrawalRepository;

    public MemberWithdrawalService(final MemberRepository memberRepository,
                                   final WithdrawalRepository withdrawalRepository) {
        this.memberRepository = memberRepository;
        this.withdrawalRepository = withdrawalRepository;
    }

    @Transactional
    public void withdrawal(final long memberId, final MemberWithdrawalRequest dto) {
        Member member = memberRepository.getById(memberId);
        memberRepository.delete(member);
        Withdrawal withdrawal = WithdrawalMapper.toEntity(memberId, dto);
        withdrawalRepository.save(withdrawal);
    }
}
