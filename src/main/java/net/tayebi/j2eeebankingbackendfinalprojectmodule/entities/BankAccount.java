package net.tayebi.j2eeebankingbackendfinalprojectmodule.entities;

import jakarta.persistence.*;
import lombok.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@Entity
// strategy 1 single table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",length=4,discriminatorType=DiscriminatorType.STRING  )

// Strtegy 2 table per class
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// Strategy 3 de joined table
//@Inheritance(strategy = InheritanceType.JOINED)
//@Data @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class BankAccount {// 1 pour single table
//public abstract class  BankAccount {//  2 pour tbale per class
//public  class  BankAccount { //  3 pour joined table
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    //un compte comcrne un seul client
    @ManyToOne
    private  Customer customer;
    // peut contneir avoir plusieurs operations
    //un compte peux avoir plusieurs operations
@OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;
}
