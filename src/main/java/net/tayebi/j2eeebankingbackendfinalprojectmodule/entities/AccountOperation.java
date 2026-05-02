package net.tayebi.j2eeebankingbackendfinalprojectmodule.entities;

import jakarta.persistence.*;
import lombok.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.OperationType;

import java.util.Date;
@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AccountOperation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    // chaque operation concerne un compte
    @ManyToOne
    private  BankAccount bankAccount;

}
