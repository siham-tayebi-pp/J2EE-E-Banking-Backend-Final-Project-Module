## 🚀 1ère étape : Ajouter les dépendances nécessaires
- Spring Web : permet de créer des applications web et de gérer les requêtes HTTP (controllers, APIs REST, etc.).
- Spring Data JPA : permet d’interagir facilement avec la base de données en utilisant des entités Java (ORM avec Hibernate).
- Lombok : bibliothèque qui réduit le code répétitif en générant automatiquement les getters, setters, constructeurs, etc.
- H2 Database : base de données en mémoire utilisée pour le développement et les tests.
- Spring Boot DevTools (optionnel) :
 * redémarrage automatique de l’application lors des modifications
 * rechargement automatique des fichiers HTML (Thymeleaf)
 * configuration optimisée pour le développement
## 💡 Remarques:
Avec DevTools, il n’est pas nécessaire de redémarrer l’application après chaque modification (surtout pour les fichiers HTML).
La console H2 peut être activée avec :spring.h2.console.enabled=true
Elle permet de visualiser les données de la base directement dans le navigateur.

## 🎯 Objectif

Mettre en place l’environnement de base nécessaire pour développer une application Spring Boot avec :

- une interface web
- une base de données
- une structure propre et maintenable

## 2 me etpae on passe a creer nots pacjge
entities
repositories 
services 
web
dtos
mappers
## On cree les entity
BankAccount
Customer
AccountOperation
CurrentAccount
SavingAccount
mapping c jpa
mongodb pas entity mais doccumemtn soit mapoing obj relatioenl ou mapping obj document

mapped by cad relation deja prenste dans une classe 
## on passe a lheritae ya 3 stratgeie
- single table une seul tab contieent tt les types de filles avec que attribut du fils non null
- table per class la on nbre de tbal sc nmre de fils 
- table joined la on 3 tab 1 mer e et les fils ayant que att perosnlai _+ id du mere
## APres avoir crre tt les ntiites et lconfigurer notre fich application,propeties on lance lapp et voil les tab sont cree avec succe s
![1.png](images/1.png)
## on passe a repositories ou on  va creer 3 interfaces
on creer accountbank repository 
accoutaoperatinrepository et custoemr repository 
et apres on passe a injecter le cosutmer repository , account repo et ope;;; repodans e cmd line runner avec maan totaion bean pour creer des cotumr dan sbd des demarrage de alpp
```java
@Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, CustomerRepository customerRepository2) {
        return args -> {
            Stream.of("Hassan","Imane","Siham").forEach(customer -> {
                Customer c=new Customer();
                c.setName(customer);
                c.setEmail(customer+"@gmail.com");
                customerRepository.save(c);
            });
        };
    }
```
Et voil anos coustumers ajoute avce succes 
![2.png](images/2.png)
ET voila nos donnes ajoute a bank accoutn 
![3.png](images/3.png)
la ya un petiit prob dan sles enums il stocke snum au leiu de string c pour cela on va ajoute r
```java
@Enumerated(EnumType.STRING)
```
Et voila la resultat
![4.png](images/4.png)
Maintnenat on passe a creer des accoutn operation 
aevc emme priencipe 
et voila il sont cree dans notre bd
![5.png](images/5.png)
## On essaye la strateguie 2 tabke prclasse
EN mettant dans la classe mrre 
```java

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
```
voila notre tab current accoutn ms la ya aussi tab accoutn nabnk la atb de a classe emre 
![6.png](images/6.png)
en mettant aussi abstart dans clase mere pour aque il ne la cree  pass

```java
public abstract class  BankAccount {
    //...............
}
```
La ya pas de bacnk account la class mere car abstrac t c poue clea que hibernat e na a sla genrer auto 
![7.png](images/7.png)

## Maintennt in passe a la 3eme startegie Joined tabl 
La on emt dans classe mere 
```java
@Inheritance(strategy = InheritanceType.JOINED)

```
La aoon a 3 tab mere et les deux fils
1 er tab
![8.png](images/8.png)
2 eme tab
![9.png](images/9.png)
3 eme tab
![10.png](images/10.png)

## On rotrn vers csingle tabl e car c lui le plsu pratiaue dan ssc ecas car ya que un attr dans une seul classe derive
## on nabscule aussi vr smysql on chaue le dependance he h2 abevc de m,ysql
```xml
<dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
```
on change auussi les congifg dans application.propertis 
```xml
spring.application.name=J2EE-E-Banking-Backend-Final-Project-Module
spring.datasource.url=jdbc:h2:mem:bank-db
spring.h2.console.enabled=true
server.port=8089
spring.datasource.username=sa
spring.datasource.password=

```
on met 
```xml
spring.application.name=J2EE-E-Banking-Backend-Final-Project-Module
spring.datasource.url=jdbc:mysql://localhost:3306/bank-db?createDatabaseIfNotExist=true
server.port=8089
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.username=root
spring.datasource.password=

```
Et voia notre bd sql est cree avec succes
![11.png](images/11.png)
On passe a modifier aun account par son id
## c important de chosir le fetch dan le elt aynt maped by keybsoit eager ou lazy , alzy charhce a la demande ms eager chaer en echargant les donnes en meme temps

![12.png](images/12.png)
## 2 eme solution c utilsier les servcies avec transactioanl et injecter servcie dna scmd liner runner et ca va marcher meme avec lazzy

![12.png](images/12.png)
## on integre lapi slf4j pour integerer de la journalsition dasn mes services
## on passe a creer les exceptiosn personalise comme customer note focund exceptions dans repo des exceptions
Une Runtime Exception = une erreur qui se produit pendant l’exécution du programme (
Runtime Exception = erreur causée par un problème dans le code (logique)
👉 Java ne t’oblige pas à la gérer
Exception surveillée → tu dois gérer
👉 Exception non surveillée → tu peux gérer, mais pas obligé
non surveille rhite de runtime et c pas la peine de try catch ou htrows 
ms surveille nec essit throw s ou try catch et herite de exctption
## On implemente tt les meth du service e on cree des clase des exxcpetion personlaise et non survleii cad qui herite de exception
# ON continue avec meth de credit debit et virememt 
## on test nos meth via commanld line runner avec bean poour le lancer auto
```java
@Bean
CommandLineRunner commandLineRunner(BankAccountService bankAccountService){

    return args -> {
 // ajouter une liste de customers a tab des customrs
            Stream.of( "SIHAM","IMANE","ALI","HASAN","FOUZIA","HAMID").forEach(
        nom->{
Customer customer=new Customer();
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
List<BankAccount> bankAccounts= bankAccountService.listBankAccounts();
                    for(BankAccount account:bankAccounts){
        for (int i=0; i<10;i++){
        bankAccountService.debit(account.getId(), Math.random()*10000,"Debit"+(i+1));
        bankAccountService.credit(account.getId(), Math.random()*9000,"Credit"+(i+1));

        }
        }
        } catch (CustomerNotFoundException | BankAccountNotFoundException | BalanceNotSufficientException e) {
        e.printStackTrace();
                }
                        });


```
on change ddl hibernate en create pour verigie si ca marc he
e voila ntre tsble des customers bien ajoute
![13.png](images/13.png)
![14.png](images/14.png)
![15.png](images/15.png)
## on a finit avec couche service on passe a chousce web
## On passe a chouche web rest api
on cree cCustomerRestController
On ajoute la route customers
la on affiche les res de la req 
![16.png](images/16.png)
ca pcq on a dans customer accoutn aone to may pour resoudre prob on va ajouter fetchjspon
```java
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
```
il va serialise nom eamil et lsite des comptes il va retrn les ompte puis il va voir que un compte appertien a un cotumr il va lafficher puis compte puis costumer c pour cela on fai tjpson property cad quan d je cosnulet un client ps besoin de consulter liste des comptes dire
dire a lapi qui convertit en json , c pas la peine de serialise r les cmptes de use en mode lecture
![17.png](images/17.png)
json property c pour igneore en lecture ou ecriture et json ignore pour igneorer tt
#### DTOS
👉 DTO = Data Transfer Object

➡️ C’est une couche de classes qui servent uniquement à transporter des données entre :

le client (frontend / API)
le backend (services, controllers)
Et anmmmapper c pour transformer entity en dto ou liverse
voici une meth depxple
```java
public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }
```
Au liu de fair e set ge ya une meth qui copie les proprietes dun obj dans un autre
```java

        BeanUtils.copyProperties(customer, customerDTO);
```
ya aussi map struct qui genre tt
```java
nous on defiinit que classe lui gerne vai map struct
```

apres vaoir creer notre rest controller
```java
package net.tayebi.j2eeebankingbackendfinalprojectmodule.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.CustomerDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.Customer;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.CustomerNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
//@RequestMapping("/customers") cad faux mette ca avnt les routes dici ppiur les y acceder
public class CustomerRestController {
    BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("customers/{id}")
    public CustomerDTO customer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return  bankAccountService.getCustomer(id);

    }

}


```
ON teste

c bon a mis cusomers/id et ca a retrn lcustomer avec id 1
![18.png](images/18.png)
```java

    @PostMapping("/customers")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {

        return bankAccountService.saveCustomer(customerDTO);
    }

}
```
APrws vaoi ajoyter cette route dajout comme ele st post pas get on peux pas la tester avec  beowser on va la tester avec postman

![19.png](images/19.png)
On dit a postman dans route post de /costmores pour ajouter un nv coutomer dans header 
![20.png](images/20.png)
On a essaye dajuter un nv customr
![21.png](images/21.png)
et voila c bien ajkoute
![22.png](images/22.png)
On test si tt nos customers sont tat avec route get / cusotmers
![23.png](images/23.png)
![24.png](images/24.png)
![25.png](images/25.png)
on fait le meth de maj
Laon a creer un restfull controelle ou rest api controller  co
## On fait update  a ladrees mail de jalal avec jala2@gami.com
![26.png](images/26.png)
## on test le delete et on supprime brahim par exple
![27.png](images/27.png)
## On ajpute la dependency de sawgger pour rendre notre web service consomable va une interface cad in creer un api a pari de note web service
![28.png](images/28.png)

## on teste nos routes depuis linterface swagger
## on test GET/customers
![29.png](images/29.png)
![30.png](images/30.png)

## POST /customers
![31.png](images/31.png)
![32.png](images/32.png)
## GET/customers/{id}
![33.png](images/33.png)
## DELETE/customers/{id}
![34.png](images/34.png)

## PUT/customers/{customerId}
![35.png](images/35.png)
![36.png](images/36.png)
c ca ntre documentation de swagger rest api
Et pour consuletrer  le documentation  de notre api rest full on va tapper on tappe 
```thymeleafurlexpressions
http://localhost:8089/v3/api-docs
```
qui  decrit nmore aapi rest full
![37.png](images/37.png)
![38.png](images/38.png)
a partir ce doc on peut gnrer des classes , car ca il decrit linterface du eweb service cad les differnt opreraion et c va veux quoi en input et quoi en output
car sil on veux creer un appp et communiquer avec ce web servcie il  suffit davoir ce doc et a pertir de lui on peut genrner des clasees a parite des outils des outils genrer
 pour que si quelqun il veut lutilser il doit lire que ca sans vosu interrgoere a qcuauqe comment il marhce 
E on ept le consommer directemnt  cvia postamne on met daon c
On met import et on met lurl http://localhost:8089/v3/api-docs
puis on cvlaide 
![39.png](images/39.png)  
ET voila il nous a creer uopen ai doc defintion avec tt les opreation a etster auttomatqumenr
![40.png](images/40.png)

Voila get customers auot
![41.png](images/41.png) ya tt les routes  a tester automatiuememnt
matienntnat a chaue fois jajoute des web services il seront ajouter automatiuemem
### Mainetnent on pase au partie des compte avec virement debit credit etc
## ob cree un autre BankACount restc ontroller

## on cree une dclasse bank accoutn dto  quelle sra utilsie rporu transfer les donnes du rest api/controllers au front end pour ne treasfrer que donne dont on aura besoins

## APRes avoir creer la roulte qui reccuprer tt les opretaiosn dun compte
```java
package net.tayebi.j2eeebankingbackendfinalprojectmodule.web;

import lombok.AllArgsConstructor;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.AccountOperationDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.dtos.BankAccountDTO;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.entities.BankAccount;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.exceptions.BankAccountNotFoundException;
import net.tayebi.j2eeebankingbackendfinalprojectmodule.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// pour faire linjecion des dependances
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return  bankAccountService.getBankAccount(accountId);
    }
        @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.listBankAccounts( );

    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable  String accountId){
        return bankAccountService.accountHistory(accountId);

    }


}

```
## NOus avons aussi configurer lendpoints pour consulter un compte avec son id
![47.png](images/47.png)

## NOUs avons tester vai beowser
![42.png](images/42.png)
## On passe a faire la pagination
```java
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
}
```

```java
@GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable  String accountId , @RequestParam (name = "page", defaultValue = "0") int page, @RequestParam (name = "size", defaultValue = "5")  int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page,size);

    }
```
![43.png](images/43.png)
![44.png](images/44.png)
## on affcihe page 2
![45.png](images/45.png)
## ON affcihe page 2  size 3
![46.png](images/46.png)

## Voic l nos tt endpoints 
![48.png](images/48.png)

## ON passe au fornt end
## la on va utilsier angular
# on va installer nos tolls vai la cmd  
## pour creer nos app
## Installation de angular Cli qui permet de :
Creer un projet angular avec ng new
lancer un serveur via ng serve
generer des composants via ng generate component
builder lapp avec ng build

```text
npm install -g @angular/cli 
```
On cree un nv proekt via 
```text
ng new e-banking-app-j2ee
```
![49.png](images/49.png)
On ajoute al route serahc
http://localhost:8089/customers/search?keyword=SIHAM
voila son twst ca marche 
on test sans mettre laucun nom et ca va affciher tt  les customers
![51.png](images/51.png)
http://localhost:8089/customers/search
![50.png](images/50.png)

On test aevc http://localhost:8089/customers/search?keyword=H
![52.png](images/52.png)

