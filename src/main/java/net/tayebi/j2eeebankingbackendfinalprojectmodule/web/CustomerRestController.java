package net.tayebi.j2eeebankingbackendfinalprojectmodule.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.CustomerDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.Customer;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.CustomerNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping("/customers") //cad faux mette ca avnt les routes dici ppiur les y acceder
public class CustomerRestController {
    BankAccountService bankAccountService;
    @GetMapping

    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }

    @GetMapping("/search")

    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    public List<CustomerDTO> searchCustomers(@RequestParam( name="keyword",defaultValue="") String keyword){
        return bankAccountService.searchCustomers(keyword);
    }

    @GetMapping("/{id}")

//    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @PreAuthorize("hasAuthority('SCOPE_USER')")

    public CustomerDTO customer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return  bankAccountService.getCustomer(id);

    }

    @PostMapping

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
//    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")

    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {

        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable long id, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerDTO.setId(id);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
//    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")

    public void deleteCustomer(@PathVariable Long id)  {
        bankAccountService.deleteCustomerById(id);
    }

}
