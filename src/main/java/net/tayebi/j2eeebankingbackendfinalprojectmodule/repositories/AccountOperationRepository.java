package net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories;

import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
