package net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {


        private Long id;
        private String name;
        private String email;
       }

