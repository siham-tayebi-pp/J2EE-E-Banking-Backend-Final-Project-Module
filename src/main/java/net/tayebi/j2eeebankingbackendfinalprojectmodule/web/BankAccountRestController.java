package net.tayebi.j2eeebankingbackendfinalprojectmodule.web;

import lombok.AllArgsConstructor;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BalanceNotSufficientException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BankAccountNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.services.BankAccountService;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")

// pour faire linjecion des dependances
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/accounts/{accountId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return  bankAccountService.getBankAccount(accountId);
    }
        @GetMapping("/accounts")
        @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.listBankAccounts( );

    }
    @GetMapping("/accounts/{accountId}/operations")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<AccountOperationDTO> getHistory(@PathVariable  String accountId){
        return bankAccountService.accountHistory(accountId);

    }
    @GetMapping("/customers/{customerId}/accounts")
//    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<BankAccountDTO> getAccounts(@PathVariable  Long customerId){
        return bankAccountService.accountsByCustomer(customerId);

    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public AccountHistoryDTO getAccountHistory(@PathVariable  String accountId , @RequestParam (name = "page", defaultValue = "0") int page, @RequestParam (name = "size", defaultValue = "5")  int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page,size);

    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }


}
