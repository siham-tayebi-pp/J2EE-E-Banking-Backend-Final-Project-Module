package net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos;

import jakarta.persistence.*;
import lombok.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.OperationType;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountOperationDTO {
  private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private BankAccount bankAccount;
    private String description;

}
