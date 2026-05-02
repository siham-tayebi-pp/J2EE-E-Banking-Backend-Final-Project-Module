package net.tayebi.j2eeebankingbackendfinalprojectmodule.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
// stragtegy single table
@DiscriminatorValue("SA")
// joined et perclass on fiat rien ici
public class SavingAccount extends BankAccount {
    private double interestRate;
}
