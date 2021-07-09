US185 Check The Balance Of One Of My Accounts
=======================================

# 1. Requirements

*As a family member, I want to check the balance of one of my accounts.*

From the project proposal: *"(...) the balance of a current/checking account usually reflects the difference between
deposits and withdrawals, while the balance of a credit card account is the amount due at that moment, including
interest (...)"*

The functionality will allow a family member to check the balance of one of their personal accounts by selecting which
of their personal accounts they want, if they have more than one.

## 1.1 System sequence diagram

The system sequence diagram below represents the interaction between Family Member and the Application.

```puml
@startuml
autonumber
header SSD
title Check the balance of one account
actor "Family Member" as FM
participant ": Application" as App

FM -> App : Check account balance
activate FM
activate App
App --> FM : Asks to select one of the account's
deactivate App

FM -> App : Select desired account
activate App
App --> FM : Show respective account balance
deactivate App
deactivate FM

@enduml
```

# 1.2. Dependency of other user stories

This User Story has dependencies with [US010](US010_Create_Family.md), [US011](US011_Add_Family_Administrator.md) and [US101](US101_Add_Family_Member.md)
because a family, a family administrator and family members must exist in order for this US to be operational. This user
story is also dependent on [US170](US170_Create_Personal_Cash_Account.md), [US171](US171_Add_Personal_Bank_Account.md), [US172](US172_Add_Bank_Savings_Account.md) and [US173](US173_Add_Credit_Card_Account.md)
because we need to have accounts added in order to check their balance.

# 2. Analysis

### 2.1 Get Account Balance

An account object should have the following attributes:

| Attributes        | Class                                                                        |                      
| ----------------- | -----------------------------------------------------------------------------|                      
| **accountId**         | Unique, UUID                                                                 |                       
| **accountOwner**      | Required, Ownership                                                          |                      
| **currency**          | Required, Currency                                                           |                      
| **transactionList**   | List of transactions                                                         |                      
| **description**       | String, a default description is provided if left blank by the family member |

### 2.2 Relevant domain model excerpt

The relevant domain concepts for this user story:

```puml
@startuml
hide methods

object Person {
- personId
- name
- birthDate
- emailAddressList
- Address
- phoneNumber
- VAT

}

object PersonalCashAccount {
- accountId
- accountOwner
- designation
  }

object BankAccount {
- accountId
- accountOwner
- designation
  }
  
  object BankSavingsAccount {
- accountId
- accountOwner
- designation
- issuer
  }
  
  object CreditCardAccount {
- accountId
- accountOwner
- designation
- issuer
- cardToken
- creditLimit
- interestRate
  }

object Transaction {
    # transactionId
    # timestamp
    # amount
    # postBalance  
    # description
    # category
    # customTimestamp
}


object MonetaryValue {
    - value
    - currency
}

PersonalCashAccount "0..1" -- "1" Person : has  
BankAccount "0..*" -- "1" Person : has          
BankSavingsAccount "0..*" -- "1" Person : has   
CreditCardAccount "0..*" -- "1" Person : has    

MonetaryValue -* Transaction::amount

Transaction -- PersonalCashAccount: has list of <
Transaction -- CreditCardAccount: has list of <
Transaction -- BankAccount: has list of <
Transaction -- BankSavingsAccount: has list of <
@enduml
```


# 3. Design

## 3.1. Functionality Development

The process consists in getting the list of all accounts, and then, after getting them, select the desired account
and get the balance. The Sequence Diagram for this user story is as follows:

### 3.1.1 Sequence diagrams

```puml
autonumber
header SD
title Check the balance of one account of this person

actor "Family Member" as FM
participant ": UI" as UI
participant ": CheckPersonalAccount\nController" as CPAC
participant "AccountService\n:AccountService" as FAS
participant "aPersonId \n: Email" as email
participant "accountRepository\n:AccountRepository" as AR
participant "accountListOutputMapper\n:AccountsListOutputMapper" as ALOM
participant "accountListOutputDTO\n:AccountListOutputDTO" as DTO
participant "personRepository\n:PersonRepository" as PR
participant "aFamilyId \n: FamilyId" as familyId
participant "familyRepository\n:FamilyRepository" as FR

FM -> UI : Show account list
activate FM
activate UI
UI-> CPAC : getAccountList(personId)
activate CPAC

CPAC -> FAS : getAccountList(personId)
activate FAS

FAS --> email** : create(personId)
FAS -> PR : getByEmail(aPersonId)
activate PR
ref over PR
  getByEmail()
end ref
PR --> FAS : aPerson
deactivate PR
FAS --> PR: aPerson.getPersonalAccounts(aPerson)
activate PR
ref over PR
getPersonalAccountsList()
end ref
PR --> FAS: personalAccountsIds
deactivate PR

FAS -> familyId** : aPerson.getFamilyId()
familyId --> FAS : aFamilyId
activate familyId
deactivate familyId
FAS -> FR : getByFamilyId(aFamilyId)
activate FR
ref over FR
  getByFamilyId()
end ref
return aFamily

FAS -> FR: getFamilyCashAccount(aFamily)
activate FR
ref over FR
getFamilyCashAccount()
end ref
return familyCashAccountId
deactivate FR

FAS --> ALOM**: create

FAS -> ALOM : mapAccountList(personalAccountsIds, familyCashAccountId)
activate ALOM
ALOM --> DTO** : create (List<>)
return accountListOutputDTO



FAS --> CPAC:  accountListOutputDTO
deactivate FAS
CPAC --> UI: accountListOutputDTO
deactivate CPAC

UI --> FM : Show List of accounts of this member
deactivate UI

FM -> UI : Select account
activate UI 

UI -> CPAC : getAccountBalance(accountId)
activate CPAC
CPAC -> FAS : getAccountBalance(accountId)
activate FAS
FAS -> AR : getBalance(accountId)
activate AR
ref over AR
getBalance()
end ref
AR --> FAS : balance
deactivate AR
FAS --> CPAC : balance 
deactivate FAS
CPAC --> UI : balance
deactivate CPAC
UI --> FM : Show balance of selected account
deactivate UI
deactivate FM



```

```puml
@startuml
header SD
autonumber
title getByEmail()

participant "personRepository \n: PersonRepository" as PR
participant "anEmailJPA\n: EmailJPA" as emailjpa
participant ": iPersonRepositoryJPA" as iPRJPA
participant "personDomainDataAssembler \n: PersonDomainDataassembler" as DA
participant "aPersonJPA \n: PersonJPA" as aPersonJPA
participant "aPerson\n: Person" as aPerson

-> PR : getByEmail(aPersonId)
activate PR
PR --> emailjpa** : create (aPersonId.getEmailAddress)
PR -> PR : fromOptionalToPersonJPA(anEmailJPA)
PR -> iPRJPA : findById(anEmailJPA)
activate iPRJPA

iPRJPA --> PR : aPersonJPAOptional
deactivate iPRJPA

|||
PR -> aPersonJPA** : personJPAOptional.get()
PR -> PR : fromJPAToDomain(aPersonJPA)
PR -> DA : JPAValueObjectsToDomain(aPersonJPA)
activate DA
DA --> PR : personValueObjects
deactivate DA
|||
PR -> aPersonJPA : getId()
activate aPersonJPA
aPersonJPA --> PR : aPersonIdJPA
deactivate aPersonJPA
PR -> iPRJPA : getOtherEmailsById(aPersonIdJPA)
activate iPRJPA
iPRJPA --> PR : otherEmailAddressesJPA
deactivate iPRJPA
|||
PR -> DA : emailAddressesJPATODomain(otherEmailAddressesJPA)
activate DA
DA --> PR : emailAddresses
deactivate DA
|||

PR --> aPerson** : create(personId, personName, birthdate, personVat, familyId, address, phoneNumbers, emailAddresses)
PR -> PR : personValueObjects.getPhoneNumbers()
loop for every phoneNumber in phoneNumbers
  PR -> aPerson : addPhoneNumber(phoneNumbers.get)
  activate aPerson
  deactivate aPerson
end
loop for every emailAddress in emailAddresses
  PR -> aPerson : addEmail(emailAddress.get)
  activate aPerson
  deactivate aPerson
end
<-- PR : aPerson
  deactivate PR

@enduml
```

```puml
@startuml
header SD
autonumber
title getByFamilyId()

participant "familyRepository\n: FamilyRepository" as FR
participant "familyIdJPA\n: FamilyIdJPA" as familyIdJPA
participant "iFamilyRepositoryJPA\n: IFamilyRepository" as ifamrep
participant "familyDomainDataAssembler\n: FamilyDomainDataAssembler" as assembler
participant "aFamily\n: Family" as fam

-> FR : getByFamilyId(familyId)
activate FR
FR --> familyIdJPA** : create(familyId)
FR -> FR : fromOptionalToFamily(familyIdJPA)
FR -> ifamrep : findById(familyIdJPA)
activate ifamrep
ifamrep --> FR : familyJPAOptional
deactivate ifamrep
FR -> assembler : fromOptionalToFamilyVOs(familyJPAOptional)
activate assembler
assembler --> FR : familyOutputVOs
deactivate assembler
FR --> fam** : create(familyName, email, registrationDate, familyId)
FR -> fam : setAccountId(familyOutputVOs.getAccountId)
activate fam
FR -> fam : setFamilyMembers(familyOutputVOs.getFamilyMembers)
FR -> fam : setFamilyRelationships(familyOutputVOs.getFamilyRelationships)
deactivate fam
<-- FR : aFamily
deactivate FR

@enduml
```

## 3.2. Class Diagram

The Class Diagram is the following:

```plantuml
@startuml
title Class Diagram US185
hide empty members

class CheckAccountBalanceController {
 - application : Application

 + getListOfAccountsOfThisOwner()
 + getAccountBalance()
}

class Application{
  - accountService: AccountService
  - familyService : FamilyService
  - personService : PersonService
  - transactionService: TransactionService 
  - loggedPerson

  + getAccountService()
  + getFamilyService()
  + getPersonService()
  + getTransactionService()
  + setLoggedPerson()
  + getLoggedPerson()
  }
  
  class AccountService{                          
  - accountList : List<Account>                                                                  
  + hasAccount()                  
  + getListOfAccountsOfThisOwner()
  + getAccountBalance()
  }  
  
class Person{
 - personId 
 - name 
 - birthDate 
 - vat
 - address 
 - emailAddressList 
 - phone                              
  }


abstract class Account {

 # accountID                  
 # accountOwner            
 # currency          
 # transactionList     
 # designation       
                    
 + getBalance()              
 + getAccountOwner()        
}

abstract class Transaction {
    # transactionId: UUID
    # description: String
    # category: Category
    # timestamp: Date
    # customTimestamp: Date
    # amount: MonetaryValue
    # postBalance: MonetaryValue
}

class AccountOwner{
 - type
 - name
 + getName() 
}
 
class MonetaryValue {    
  - value : BigDecimal            
  - currency : Currency 
  
  + createMonetaryValue()
}             
              
interface Ownership {
  getOwnerName()   
}


CheckAccountBalanceController "1"-"1" Application
Application "1"--"1" AccountService : calls
AccountService "  1 " *- " 0..* " Account : contains >
Account "1 " - "    0..1 " AccountOwner :  has
MonetaryValue -* Transaction::amount
Account "  1 " *-- " 0..* " Transaction : contains >
Ownership <|.u. Person
class AccountOwner extends Ownership 

@enduml
```

## 3.3. Applied patterns

_Place_Holder_

## 3.4. Testes

### 3.4.1 Unit Tests

The Unit tests are the following:

### Success Cases

**Test 1:** Get the balance of a cash account successfully.

```java
    @Test
    void getCashAccountBalanceSuccessfully_OneAccount(){
            accountService.createPersonalCashAccount(person,initialAmount,currency);
            Account account=accountService.getCashAccountByOwner(person);
            AccountDTO accountDTO=new AccountDTO(account.getAccountId(),account.getDesignation());
            MonetaryValue money=new MonetaryValue(500);
            MonetaryValueMapper expected=new MonetaryValueMapper(money);

            MonetaryValueDTO result=accountService.getAccountBalance(accountDTO);

            assertEquals(expected.toDTO(),result);
            }
```

**Test 2:** Get the balance of a bank account successfully when the user has a bank account, and a personal cash
account.

```java
    @Test
    void getBankAccountBalanceSuccessfully_BetweenTwoDifferentTypeOfAccounts(){
            accountService.createPersonalCashAccount(person,initialAmount,currency);
            Account account=accountService.getCashAccountByOwner(person);
            accountService.createBankAccount(person,initialAmount,currency,designation);
            Account account1=accountService.getBankAccountByOwner(person);
            AccountDTO accountDTO=new AccountDTO(account1.getAccountId(),account1.getDesignation());
            MonetaryValue money=new MonetaryValue(500);
            MonetaryValueMapper expected=new MonetaryValueMapper(money);

            MonetaryValueDTO result=accountService.getAccountBalance(accountDTO);

            assertEquals(expected.toDTO(),result);
            }
```

**Test 3:** Get the balance of a bank savings account successfully when the user has a bank account, a personal cash
account and a bank savings account.

```java
@Test
    void getBankSavingsAccountBalanceSuccessfully_BetweenFourDifferentTypeOfAccounts(){
            accountService.createPersonalCashAccount(person,initialAmount,currency);
            Account account=accountService.getCashAccountByOwner(person);
            accountService.createBankAccount(person,initialAmount,currency,designation);
            Account account1=accountService.getBankAccountByOwner(person);
            accountService.createBankSavingsAccount(person,initialAmount,currency,issuer,designation);
            Account account2=accountService.getBankSavingsAccountByOwner(person);
            AccountDTO accountDTO=new AccountDTO(account2.getAccountId(),account2.getDesignation());
            MonetaryValue money=new MonetaryValue(500);
            MonetaryValueMapper expected=new MonetaryValueMapper(money);

            MonetaryValueDTO result=accountService.getAccountBalance(accountDTO);

            assertEquals(expected.toDTO(),result);
            }
```

### Unsuccessful Cases:

# 4. Implementation

Some challenges encountered during the development process and implementation of this functionality
were:

- The dependency on other functionalities to be fully operational to implement and test this
  functionality correctly;
- The need to implement two DTO, one to have the list of accounts of a particular family member, 
  and another DTO to show to the desired account balance.

In order to minimize these problems and to guarantee a reliable
functionality, the tests were done to cover the maximum scenarios, identifying
and correcting the encountered errors. In addition, there was communication with
the Product Owner whenever needed, to clarify some requirements in relation to this specific
functionality.


# 5. Integration/Demonstration

As stated in the [Requirements](#1-requirements), the use-case scenario will only be possible if some
other functionalities are fully operational.

# 6. Observations

- Abstract classes are used in order to reduce code duplication.

[us010]: US010_Create_Family.md

[us101]: US101_Add_Family_Member.md

[us105]: US105_Create_Relationship.md

[us170]: US170_Create_Personal_Cash_Account.md

[us171]: US171_Add_Personal_Bank_Account.md

[us172]: US172_Add_Bank_Savings_Account.md

[us173]: US173_Add_Credit_Card_Account.md

