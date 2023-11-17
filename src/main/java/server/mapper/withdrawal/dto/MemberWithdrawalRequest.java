package server.mapper.withdrawal.dto;

import server.domain.withdrawal.vo.WithdrawalReason;

public record MemberWithdrawalRequest(
        WithdrawalReason withdrawalReason,
        String otherReason
) {
}
