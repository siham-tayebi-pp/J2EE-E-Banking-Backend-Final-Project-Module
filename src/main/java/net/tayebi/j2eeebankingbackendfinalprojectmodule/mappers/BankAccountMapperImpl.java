package net.tayebi.j2eeebankingbackendfinalprojectmodule.mappers;

import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.AccountOperationDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.CurrentBankAccountDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.CustomerDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.SavingBankAccountDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.AccountOperation;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.CurrentAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.Customer;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }
    public  Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
//        customer.setId(customerDTO.getId());
//        customer.setName(customerDTO.getName());
//        customer.setEmail(customerDTO.getEmail());
        return customer;

    }
    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount) {
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;

    }
    public SavingAccount fromSavingAccountDTO(SavingBankAccountDTO savingBankAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));

        return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        return currentBankAccountDTO;

    }
 public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
 }
 public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
       AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
       BeanUtils.copyProperties(accountOperation,accountOperationDTO);
       return accountOperationDTO;
 }

}
