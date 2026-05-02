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
@DiscriminatorValue("CR")
public class CurrentAccount extends BankAccount {
    private double overDraft;
}
