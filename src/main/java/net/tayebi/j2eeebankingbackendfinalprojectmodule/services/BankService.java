package net.tayebi.j2eeebankingbackendfinalprojectmodule.services;

import jakarta.transaction.Transactional;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.CurrentAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.SavingAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public  void consulter (){
        BankAccount bankAccount1 = bankAccountRepository.findById("3cba101b-02dd-44db-a619-424fba1186aa").orElse(null);

        if (bankAccount1 != null) {
            System.out.println("*************************************");
            System.out.println(bankAccount1.getId());
            System.out.println(bankAccount1.getBalance());
            System.out.println(bankAccount1.getStatus());
            System.out.println(bankAccount1.getCreatedAt());
            System.out.println(bankAccount1.getCustomer().getName());
            if (bankAccount1 instanceof CurrentAccount) {
                System.out.println("Over Draft=>" + ((CurrentAccount) bankAccount1).getOverDraft());
                ;

            } else if (bankAccount1 instanceof SavingAccount) {
                System.out.println("Rate=>" + ((SavingAccount) bankAccount1).getInterestRate());


                bankAccount1.getAccountOperations().forEach(
                        op -> {
                            System.out.println("****************");
                            System.out.print(op.getType() + "/t");
                            System.out.print(op.getAmount() + "/t");
                            System.out.print(op.getOperationDate() + "/t");
                        }
                );}

        }
    }
}
