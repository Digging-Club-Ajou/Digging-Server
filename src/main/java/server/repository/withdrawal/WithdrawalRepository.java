package server.repository.withdrawal;

import org.springframework.stereotype.Repository;
import server.domain.withdrawal.persist.Withdrawal;

@Repository
public class WithdrawalRepository {

    private final WithdrawalJpaRepository withdrawalJpaRepository;

    public WithdrawalRepository(final WithdrawalJpaRepository withdrawalJpaRepository) {
        this.withdrawalJpaRepository = withdrawalJpaRepository;
    }

    public void save(final Withdrawal withdrawal) {
        withdrawalJpaRepository.save(withdrawal);
    }
}
