package net.tayebi.j2eeebankingbackendfinalprojectmodule.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.AccountStatus;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.OperationType;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BalanceNotSufficientException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BankAccountNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.CustomerNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.mappers.BankAccountMapperImpl;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories.AccountOperationRepository;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories.BankAccountRepository;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
//@NoArgsConstructor

public class BankAccountServiceImpl implements BankAccountService {
    CustomerRepository customerRepository;
    BankAccountRepository bankAccountRepository;
    AccountOperationRepository accountOperationRepository;
    BankAccountMapperImpl bankAccountMapper;
//    Logger log = LoggerFactory.getLogger(this.getClass().getName()); deja fair avec api de jouranalisaion de log 4 j nomme slf4j

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving New Customer");
        Customer savedCustomer = customerRepository.save(bankAccountMapper.fromCustomerDTO(customerDTO));
        return bankAccountMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException {
        log.info("Updating New Customer");
        Customer savedCustomer = customerRepository.save(bankAccountMapper.fromCustomerDTO(customerDTO));

        return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        log.info("Deleting Customer");
        customerRepository.deleteById(customerId);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customrId) throws CustomerNotFoundException {
       Customer customer=customerRepository.findById(customrId).orElse(null);
       if (customer == null) {
           throw new CustomerNotFoundException("Customer not found");
       }
       CurrentAccount currentBankAccount=new CurrentAccount();
//       currentBankAccount.setId(UUID.randomUUID().toString());
       currentBankAccount.setBalance(initialBalance);
       currentBankAccount.setOverDraft(overDraft);
       currentBankAccount.setCustomer(customer);
        currentBankAccount.setStatus(AccountStatus.CREATED);
       currentBankAccount.setCreatedAt(new Date());
       CurrentAccount savedBankAccount=bankAccountRepository.save(currentBankAccount);
        return bankAccountMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customrId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customrId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount= new SavingAccount();

        savingAccount.setBalance(initialBalance);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setCreatedAt(new Date());
        SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);
        return bankAccountMapper.fromSavingAccount(savedBankAccount);
    }

        @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers=customerRepository.findAll();
        List<CustomerDTO> customerDTOS=new ArrayList<>();
        customerDTOS=customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());

//        for (Customer customer : customers) {
//            customerDTOS.add(bankAccountMapper.fromCustomer(customer));
//        }
        return customerDTOS;
        //1:40
    }

    @Override
    public List<BankAccountDTO> listBankAccounts() {

        List<BankAccount> bankAccounts= bankAccountRepository.findAll();
        //meth1
//        List<BankAccountDTO> bankAccountDTOS=new ArrayList<>();
//        for (BankAccount bankAccount : bankAccounts) {
//            if (bankAccount instanceof SavingAccount) {
//                bankAccountDTOS.add(bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount));
//            }else if (bankAccount instanceof CurrentAccount) {
//                bankAccountDTOS.add(bankAccountMapper.fromCurrentBankAccount((CurrentAccount) bankAccount));
//            }
//        }
//        return bankAccountDTOS;
        //meth2
        List<BankAccountDTO> bankAccountDTOS=bankAccounts.stream().map(bankAccount ->{
            if (bankAccount instanceof SavingAccount) {
                return  bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount);
            }else if (bankAccount instanceof CurrentAccount) {
                return bankAccountMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
            }
            return null;
        }).collect(Collectors.toList());


        return bankAccountDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->  new BankAccountNotFoundException("Bank account not found"));
        if(bankAccount instanceof CurrentAccount){
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentBankAccount(currentAccount);

       }
        else if (bankAccount instanceof SavingAccount) {
            SavingAccount savingBankAccount=(SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingAccount(savingBankAccount);

        }

        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->  new BankAccountNotFoundException("Bank account not found"));

        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Balance Not Sufficient Exception");
        }
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        //mettre a jour le solde
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);


    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
       Customer customer= customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
       return bankAccountMapper.fromCustomer(customer);


    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->  new BankAccountNotFoundException("Bank account not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        //mettre a jour le solde
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void virement(String acountIdSource, String acountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(acountIdSource,amount,"trandfer to"+acountIdDest);
        credit(acountIdDest,amount,"tranfer from"+acountIdSource);

    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations=accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null) {
            throw new BankAccountNotFoundException("Bank Account Not Found");
        }
     Page<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId,  PageRequest.of(page,size));
     AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
     List<AccountOperationDTO> accountOperationsDto=accountOperations.getContent().stream().map(op->bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());

     accountHistoryDTO.setAccountHistoryDTOList(accountOperationsDto);
     accountHistoryDTO.setAccountId(bankAccount.getId());
     accountHistoryDTO.setBalance(bankAccount.getBalance());
     accountHistoryDTO.setCurrentPage(page);
     accountHistoryDTO.setPageSize(size);
     accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.findByNameContains(keyword);
        List<CustomerDTO> customerDtos=customers.stream().map(customer -> {
            return bankAccountMapper.fromCustomer(customer);
        }).collect(Collectors.toList());

        return customerDtos;
    }
}
