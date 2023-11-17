package server.domain.withdrawal.persist;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.withdrawal.vo.WithdrawalReason;
import server.global.annotation.Association;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Withdrawal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawal_id")
    private Long id;

    @Association
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private WithdrawalReason withdrawalReason;

    private String otherReason;

    @Builder
    private Withdrawal(final long memberId, final WithdrawalReason withdrawalReason, final String otherReason) {
        this.memberId = memberId;
        this.withdrawalReason = withdrawalReason;
        this.otherReason = otherReason;
    }
}
