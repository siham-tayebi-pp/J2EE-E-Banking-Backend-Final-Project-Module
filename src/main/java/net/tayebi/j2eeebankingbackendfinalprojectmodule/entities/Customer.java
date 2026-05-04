package net.tayebi.j2eeebankingbackendfinalprojectmodule.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    //un client peux avoir plusieurs accounts
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)// cad dan classe bank accout ya dekja un attribut qui represnt dla relation/
    //va regrader lautre classe elle a ca sinn ca va la creer aussi icc
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> bankAccounts;

}
