package net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos;

import lombok.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.AccountStatus;

import java.util.Date;

@Data @AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurrentBankAccountDTO extends BankAccountDTO {
    private String  id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
