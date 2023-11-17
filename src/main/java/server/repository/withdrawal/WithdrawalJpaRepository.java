package server.repository.withdrawal;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.withdrawal.persist.Withdrawal;

public interface WithdrawalJpaRepository extends JpaRepository<Withdrawal, Long> {
}
