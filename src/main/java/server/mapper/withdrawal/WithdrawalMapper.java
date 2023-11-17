package server.mapper.withdrawal;

import server.domain.withdrawal.persist.Withdrawal;
import server.mapper.withdrawal.dto.MemberWithdrawalRequest;

public class WithdrawalMapper {

    private WithdrawalMapper() {
    }

    public static Withdrawal toEntity(final long memberId, final MemberWithdrawalRequest dto) {
        return Withdrawal.builder()
                .memberId(memberId)
                .withdrawalReason(dto.withdrawalReason())
                .otherReason(dto.otherReason())
                .build();
    }
}
