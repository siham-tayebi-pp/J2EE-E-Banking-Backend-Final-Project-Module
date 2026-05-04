package net.tayebi.j2eeebankingbackendfinalprojectmodule;

import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.BankAccountDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.CurrentBankAccountDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.CustomerDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.SavingBankAccountDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.AccountStatus;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.OperationType;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BalanceNotSufficientException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BankAccountNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.CustomerNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories.AccountOperationRepository;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories.BankAccountRepository;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories.CustomerRepository;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.services.BankAccountService;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class J2EeEBankingBackendFinalProjectModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(J2EeEBankingBackendFinalProjectModuleApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){

        return args -> {
            // ajouter une liste de customers a tab des customrs
            Stream.of( "SIHAM","IMANE","ALI","HASAN","FOUZIA","HAMID").forEach(
                    nom->{
                        CustomerDTO customer=new CustomerDTO();
                        customer.setName(nom);
                        customer.setEmail(nom+"@gmail.com");

                        bankAccountService.saveCustomer(customer);
                    }
            );
            bankAccountService.listCustomers().forEach(customer->
            {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

                } catch (CustomerNotFoundException  e) {
                    e.printStackTrace();
                }
            }

            );


            List<BankAccountDTO> bankAccounts= bankAccountService.listBankAccounts();
            for(BankAccountDTO accountDTO:bankAccounts){

                for (int i=0; i<10;i++){
                    String accountId="";
                    if (accountDTO instanceof SavingBankAccountDTO) {
                        accountId=((SavingBankAccountDTO)accountDTO ).getId();

                    }else if (accountDTO instanceof CurrentBankAccountDTO) {
                        accountId=((CurrentBankAccountDTO)accountDTO ).getId();
                    }
                    try {
                        bankAccountService.credit(accountId, 10000+Math.random()*120000,"Credit"+(i+1));
                        bankAccountService.debit(accountId, 1000+Math.random()*9000,"Debit"+(i+1));

                    } catch (BankAccountNotFoundException|BalanceNotSufficientException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        };

    };
    //@Bean
    //mth2 sol a 1
    CommandLineRunner commandLineRunner1(BankService bankService){

        return args -> {
//            bankService.consulter();


        };};



//    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan","Imane","Siham").forEach(customer -> {
                Customer c=new Customer();
                c.setName(customer);
                c.setEmail(customer+"@gmail.com");
                customerRepository.save(c);
            });
            customerRepository.findAll().forEach(
                    cstmr->{
                        //creer current account
                        CurrentAccount ca=new CurrentAccount();
                        ca.setCustomer(cstmr);
                        ca.setBalance(Math.random()*90000);
                        ca.setCreatedAt(new Date());
                        ca.setOverDraft(9000);
                        ca.setStatus(AccountStatus.CREATED);
                        bankAccountRepository.save(ca);


                        // creer saving account
                        SavingAccount sa=new SavingAccount();
                        sa.setCustomer(cstmr);
                        sa.setBalance(Math.random()*90000);
                        sa.setCreatedAt(new Date());
                        sa.setInterestRate(5.5);
                        sa.setStatus(AccountStatus.CREATED);
                        bankAccountRepository.save(sa);

                    }
            );
            bankAccountRepository.findAll().forEach(
                    bankAccount->{
                        for(int i=0; i<5; i++){
                            AccountOperation accountOperation=new AccountOperation();
                            accountOperation.setOperationDate(new Date());
                            accountOperation.setAmount(Math.random()*12000);
                            accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                            accountOperation.setBankAccount(bankAccount);
                            accountOperationRepository.save(accountOperation);
                        }

                    }

            );





        };
         }




}

