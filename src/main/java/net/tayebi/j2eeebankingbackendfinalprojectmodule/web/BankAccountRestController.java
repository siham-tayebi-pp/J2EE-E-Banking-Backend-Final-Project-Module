package net.tayebi.j2eeebankingbackendfinalprojectmodule.web;

import lombok.AllArgsConstructor;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.AccountHistoryDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.AccountOperationDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.BankAccountDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BankAccountNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.services.BankAccountService;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// pour faire linjecion des dependances
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return  bankAccountService.getBankAccount(accountId);
    }
        @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.listBankAccounts( );

    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable  String accountId){
        return bankAccountService.accountHistory(accountId);

    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable  String accountId , @RequestParam (name = "page", defaultValue = "0") int page, @RequestParam (name = "size", defaultValue = "5")  int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page,size);

    }


}
