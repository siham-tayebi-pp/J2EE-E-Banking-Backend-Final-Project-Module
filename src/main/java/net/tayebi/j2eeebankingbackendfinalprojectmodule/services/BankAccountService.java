package net.tayebi.j2eeebankingbackendfinalprojectmodule.services;

import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BalanceNotSufficientException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BankAccountNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
//    Customer saveCustomer(Customer customer);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException;

    void deleteCustomerById(Long customerId);



    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customrId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customrId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    List<BankAccountDTO> listBankAccounts();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void virement(String acountIdSource,String acountIdDest,double amount ) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
