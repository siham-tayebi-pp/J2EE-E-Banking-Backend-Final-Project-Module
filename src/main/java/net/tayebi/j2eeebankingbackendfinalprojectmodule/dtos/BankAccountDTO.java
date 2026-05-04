package net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos;

import jakarta.persistence.*;
import lombok.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.AccountOperation;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.Customer;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@Data

@Getter @Setter
public class BankAccountDTO {
    private String type;

}
