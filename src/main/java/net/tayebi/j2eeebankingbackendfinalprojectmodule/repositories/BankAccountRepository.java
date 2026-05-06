package net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories;

import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    void deleteBankAccountsByCustomer(Customer customer);

    List<BankAccount> findByCustomer(Customer customer);
}
