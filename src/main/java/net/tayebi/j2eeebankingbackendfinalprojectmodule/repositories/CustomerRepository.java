package net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories;

import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
