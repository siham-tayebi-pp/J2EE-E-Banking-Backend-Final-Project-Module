package net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos;

import lombok.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.AccountOperation;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationDTO> accountHistoryDTOList;
}
