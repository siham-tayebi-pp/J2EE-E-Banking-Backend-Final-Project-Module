package net.tayebi.j2eeebankingbackendfinalprojectmodule.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.CustomerDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.Customer;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.CustomerNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("http://localhost:4200")
@RequestMapping("/customers") //cad faux mette ca avnt les routes dici ppiur les y acceder
public class CustomerRestController {
    BankAccountService bankAccountService;
    @GetMapping
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }

    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam( name="keyword",defaultValue="") String keyword){
        return bankAccountService.searchCustomers(keyword);
    }

    @GetMapping("/{id}")
    public CustomerDTO customer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return  bankAccountService.getCustomer(id);

    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {

        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable long id, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerDTO.setId(id);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id)  {
        bankAccountService.deleteCustomerById(id);
    }

}
