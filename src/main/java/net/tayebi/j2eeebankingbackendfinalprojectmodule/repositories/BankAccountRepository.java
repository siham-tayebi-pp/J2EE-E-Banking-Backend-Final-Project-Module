package net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories;

import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
