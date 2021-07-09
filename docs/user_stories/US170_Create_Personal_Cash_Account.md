US170 Create a personal cash account
=======================================

# 1. Requirements

*As a family member, I want to create a personal cash account.*

According to this requirement, the user would like to create a personal cash account. Therefore, the
required input information is:

- The person to which the account will belong;
- The initial amount that the account is going to have;
- The currency of the account;
- The description of the account.

## 1.1 System Sequence Diagram

The below System Sequence Diagram (SSD) captures the interaction between the Family Member and the
Application when the Family Member wants to create a personal cash account.

```puml
@startuml US170_SSD 
Header SSD
title Create a personal cash account
autonumber

actor "Family Member" as FM
participant ": Application" as app

FM -> app : Create personal cash account
activate FM
activate app
app --> FM : ask data
deactivate app

FM -> app : inputs data
activate app
app --> FM: informs result
deactivate app
deactivate FM
@enduml
-->
```
<!-- ![US170 Domain Model](diagrams/US170_SSM.png)-->

## 1.2 Dependency of other User Stories

This User Story has dependencies with the [US101], since it will be necessary to have a member, to
create a personal cash account.

# 2. Analysis

## 2.1 Cash Account entry

According to what was presented, a personal cash account is created upon request from the Family
Member.

A personal cash account instance should have the following attributes:

  | Attributes            | Rules                                        |
  | ----------            | -------------------------------------------- |
  | accountId             | Unique, required                             |
  | initialAmountValue    | Required                                     |
  | accountDescription    | Required                                     |        


## 2.2 Domain Model Excerpt

The relevant extract of the domain model is represented below:

```plantuml
<!---
@startuml US170_DM
hide empty members

object Person {
- personId
- name
- birthDate
- vatNumber
- address
- emailAddresses
- phoneNumbers
  }

object CashAccount {
  - Account Id
  - Initial Amount Value
  - Description
}
Person "1" -- "0..*" CashAccount : has
@enduml
-->
```
<!-- ![US170 Domain Model](diagrams/US170_DM.png)-->

# 3. Design

## 3.1. Functionality Development

Regarding the creation of a personal cash account, we should accommodate the requirements specified
in [Analysis](#2-analysis).

The System Diagram is the following:

```puml
<!---
@startuml US170_SD
header SD
title Create a personal cash account
autonumber
actor "Family Member" as FM
participant ": UI" as UI
participant "accountController :\n AccountController" as AC
participant "accountService :\n IAccountService" as AS <<interface>>
participant "personId\n :Email" as pId
participant ": AccountDTOAssembler" as AA
participant "amount\n :InitialAmountValue" as initV
participant "accountDescription\n :AccountDescription" as accDes
participant "personalCashAccount\n :CashAccount" as cashAcc
participant "accountRepository :\nAccountRepository" as AR
participant "personRepository :\nPersonRepository" as PR
participant "person :\nPerson" as P

FM -> UI : Create a personal cash account
activate FM
activate UI
return ask data
deactivate UI

FM -> UI : input data
activate UI
UI -> AC : createPersonalCashAccount(accountInputDTO, personId)
activate AC

AC -> AS : createPersonalCashAccount(accountInputDTO, personID)
activate AS

AS --> pId **: create(accountInputDTO)

AS -> AA : fromDTOToInitialAmountValue(accountInputDTO)
activate AA

AA --> initV **: create(accountInputDTO)
deactivate AA

AS -> AA : fromDTOToAccountDescription(accountInputDTO)
activate AA

AA --> accDes **: create(accountInputDTO)
deactivate AA

AS --> cashAcc **: create(amount, accountDescription )
  
AS --> AR : saveCashAccount(personalCashAccount)
activate AR

ref over AR 
  saveCashAccount()
end 

AR --> AS : savedCashAccount
deactivate AR

AS -> PR : getByEmail(personId)
activate PR

ref over PR 
  getByEmail()
end 

PR --> AS : person
deactivate PR

AS -> cashAcc : getAccountId(cashAccount)
activate cashAcc
cashAcc --> AS: accountId
deactivate cashAcc

AS -> P : addAccountId(accountId)
activate P
P --[hidden]> AS
deactivate P


AS -> PR :  savePerson(person)
activate PR
ref over PR 
  savePerson()
end 
PR --[hidden]> AS
deactivate PR

AS -> AA : accountToOutputDTO(savedCashAccount)
activate AA


ref over AA
accountToOutputDTO()
end
AA --> AS : accountOutputDTO
deactivate AA

AC <-- AS: accountOutputDTO
deactivate AS

UI <-- AC: accountOutputDTO
deactivate AC

FM <-- UI: inform result

deactivate UI
deactivate FM

@enduml
-->
```
<!-- ![US170 Sequence Diagram : Create personal cash account](diagrams/US170_SD.png)-->

```puml
@startuml US170_SD_Ref
autonumber
header ref
title saveCashAccount()

participant "accountRepository\n : AccountRepository" as AR
participant "accountsDomainDataAssembler\n:AccountsDomainDataAssembler" as ADA
participant "CashAccount\n :CashAccount" as CA
participant "initialAmountValueJPA\n : InitialAmountValueJPA" as IAVJPA
participant "accountDescriptionJPA\n : AccountDescriptionJPA" as ADJPA
participant "cashAccountJPA\n :CashAccountJPA" as CAJPA
participant ":IAccountRepositoryJPA" as iRJPA <<interface>>
participant "newAccountId\n : AccountId" as AId
participant "initialAmountValue\n : InitialAmountValue" as IA
participant "accountDescription\n : AccountDescription" as AD
participant "savedCashAccount\n :CashAccount" as SCA

[-> AR: saveCashAccount(cashAccount)
activate AR
AR -> ADA: toData(cashAccount)
activate ADA
ADA -> CA: getInitialAmountValue()
activate CA
return amount
ADA -> CA: getCurrency()
activate CA
return currency
ADA --> IAVJPA ** : create(amount, currency)
ADA -> CA: getAccountDescription()
activate CA
return accountDescription
ADA --> ADJPA ** : create(accountDescription)

ADA --> CAJPA ** : create(initialAmountValueJPA, accountDescriptionJPA)
return cashAccountJPA

AR -> iRJPA: save(cashAccountJPA)
activate iRJPA
deactivate iRJPA
 
AR -> CAJPA: getAccountId()
activate CAJPA
return newAccountId

AR --> AId ** : create(newAccountId)

AR -> ADA: fromDataToInitialAmountValue(cashAccountJPA)
activate ADA
ADA -> CAJPA: getInitialAmountValue()
activate CAJPA
return amount
ADA -> CAJPA: getCurrency()
activate CAJPA
return currency

ADA --> IA ** : create(amount, currency)
ADA --> AR: initialAmountValue
deactivate ADA

AR -> ADA: fromDataToAccountDescription(cashAccountJPA)
activate ADA
ADA -> CAJPA: getAccountDescription()
activate CAJPA
return accountDescription
ADA --> AD ** : create(accountDescription)
ADA --> AR: accountDescription

deactivate ADA

AR --> SCA ** : create(initialAmountValue, accountDescription)
AR --> SCA: setAccountId(newAccountId)
activate SCA
deactivate SCA
return savedCashAccount
@enduml
```
<!-- ![US170 Sequence Diagram : Save Cash Account Ref](diagrams/US170_SD_Ref.png)-->


```puml
@startuml US170_SD_Ref_2
autonumber
header ref
title getByEmail()

participant "personRepository\n :PersonRepository" as PR
participant "anEmailJPA:\nEmailJPA" as EJPA
participant "iPersonRepositoryJPA\n: IPersonRepositoryJPA" as IPRJPA <<interface>>
participant "personDomainDataAssembler\n: PersonDomainDataAssembler" as PDDA
participant "person\n: Person" as P

[-> PR: getByEmail(aPersonId)
activate PR
PR -> P : getEmailAddress()
activate P
P --> PR : emailAddress
deactivate P
PR --> EJPA ** : create(emailAddress)
PR -> IPRJPA: getByEmail(anEmail)
activate IPRJPA
IPRJPA --> PR : personJPA
deactivate IPRJPA


PR -> PR : fromJPAToDomain(personJPa)

[<-- PR: person
deactivate PR

@enduml
```

<!-- ![US170 Sequence Diagram : Get Saved Person Ref](diagrams/US170_SD_Ref_2.png)-->


```puml
@startuml US170_SD_Ref_3
autonumber
header ref
title savePerson()

participant "personRepository\n :PersonRepository" as PR
participant "personDomainDataAssembler\n : PersonDomainDataAssembler" as PDA
participant ":IPersonRepository" as IPR <<interface>>

[-> PR: savePerson(person)
activate PR
PR -> PDA: toData()
activate PDA

return personJPA
deactivate PDA

PR -> IPR ++: save(personJPA)
return resultPerson

deactivate PR

@enduml
```

<!-- ![US170 Sequence Diagram : Add Cash Account To Person](diagrams/US170_SD_Ref_3.png)-->

```puml
@startuml US170_SD_Ref_4
autonumber
header ref
title accountToOutputDTO()

participant "accountDTOAssembler\n :AccountDTOAssembler" as AA
participant "savedCashAccount\n :CashAccount" as SCA
participant "accountOutputDTO\n :aAccountOutputDTO" as DTO


[-> AA: accountToOutputDTO()
activate AA
AA -> SCA: getAccountId()
activate SCA
return accountId
AA -> SCA: getInitialAmount()
activate SCA
return initialAmount
AA -> SCA: getCurrency()
activate SCA
return currency
AA -> SCA: getAccountDescription()
activate SCA
return accountDescription
AA -> DTO **: create(accountId, initialAmount, currency, accountDescription)
return accountOutputDTO

@enduml
```
<!-- ![US170 Sequence Diagram : Account To OutputDTO](diagrams/US170_SD_Ref_4.png)-->

## 3.2. Class Diagram

The Class Diagram is the following:

```plantuml
@startuml US170_CD
title Class Diagram US170
skinparam linetype ortho
header CD

interface ICreatePersonalCashAccountController {
    + createPersonalCashAccount()
}


class AccountController {
    + createPersonalCashAccount()
}

class AccountRepository {
}

interface IAccountRepositoryJPA <<interface>> {
}

interface IPersonalAccountService <<interface>> {
}


class PersonRepository {
    + getByEmail()
}

interface IPersonRepositoryJPA <<interface>> {
}

package "PersonAggregateJPA" {
    class PersonJPA  {
       
    }
}

package "PersonAggregate" {
    class Person <<Entity>> <<Root>> {
       
    }
}

package "AccountAggregate" {
    class CashAccount<<Entity>>{
    }
     abstract class Account<<Entity>> <<Root>>{
    }
}


class AccountId <<Value Object>>  <<Id>>{
    - id: int
}

class InitialAmount <<Value Object>> {
    - amount: double
    - currency: int 
}

class Description <<Value Object>> {
    - description: String
}

package "AccountAggregateJPA" {
    class CashAccountJPA<<Entity>>{
    }
     class AccountJPA{
    }
}


class AccountIdJPA <<Value Object>>  <<Id>>{
    - id: int
}

class InitialAmountJPA <<Value Object>> {
    - amount: double
    - currency: int 
}

class DescriptionJPA <<Value Object>> {
    - description: String
}

class PersonalAccountService  {
}

class PersonRepository  {
}

class AccountDTOAssembler  {
}

class PersonDomainDataAssembler  {
}



ICreatePersonalCashAccountController "1" <|.r. "1" AccountController
AccountController "1" --> "1" IPersonalAccountService

IPersonalAccountService "1" <|.r."1"  PersonalAccountService
PersonalAccountService "1" *-- "1" AccountRepository
PersonalAccountService "1" *-- "1" PersonRepository
PersonalAccountService "1" *-- "1" AccountDTOAssembler

PersonRepository "1" *-- "1" PersonDomainDataAssembler
PersonRepository "1" *-- "1" Person
PersonDomainDataAssembler "1" *-- "1" PersonJPA
PersonRepository "1" *-l- "1" IPersonRepositoryJPA
IPersonRepositoryJPA "1" *--- "0..*" PersonJPA : people


AccountRepository "1" *-- "1" AccountDomainDataAssembler
IAccountRepositoryJPA "1" *- "0..*" AccountJPA : accounts
IAccountRepositoryJPA "1" *-- "0..*" AccountRepository
AccountDomainDataAssembler "1" *-- "1" AccountJPA
AccountJPA "1..*" *-- "1" InitialAmountJPA
AccountJPA "1" *- "1" AccountIdJPA
AccountJPA "1" *-- "1" DescriptionJPA
CashAccountJPA -^ AccountJPA

CashAccount -^ Account
Account "1..*" *-- "1" InitialAmount
Account "1" *-- "1" AccountId
Account "1" *- "1" Description


PersonJPA "1" *-- "0..*" AccountIdJPA : has cashAccountId >
Person "1" *-- "0..*" AccountId : has cashAccount >

@enduml

```

## 3.3. Applied Patterns

The applied patterns were the following:

- *Single Responsibility Principle (SRP)*  - All classes have one responsibility, which means, only
  one reason to change;
- *Controller* - The controller (CreatePersonalCashAccountController) receives and coordinates
  system operations connecting the UI layer to the App's logic layer;
- Information Expert - Each class was assigned responsibilities that can be fulfilled because they
  have the information needed to do so;
- *Pure Fabrication* - The AccountService class is a class that does not represent a problem domain
  concept, nevertheless it was assigned a set of responsibilities to support high cohesion, low
  coupling and reuse;
- *Low Coupling* - Classes were assigned responsibilities so that coupling remains as low as
  possible, reducing the impact of any changes made to objects later on;
- *High Cohesion* - Classes were assigned responsibilities so that cohesion remains high(they are
  strongly related and highly focused). This helps to keep objects understandable and manageable,
  and also goes hand in hand with the low coupling principle.

## 3.4. Tests

### 3.4.1 Unit Tests

The Unit Tests are defined below:

**Test 1:** Create a Personal Cash Account Successfully

```java
 @Test
    void ensurePersonalCashAccountIsCreatedSuccessfully() {
            //arrange
            String description = "Personal cash account.";
            double initialAmount = 30;
            int currency = 1;
            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
            String personId = "joaobonifacio@hotmail.com";

            long accountId = 10;
            Currency outputCurrency = Currency.EUR;
            AccountOutputDTO accountOutputDTO = new AccountOutputDTO(accountId, initialAmount, outputCurrency.name(), description);
            when(personalAccountService.createPersonalCashAccount(inputDTO,personId)).thenReturn(accountOutputDTO);

            HttpStatus expectedStatus = HttpStatus.CREATED;

            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
        }
```    

**Test 2:** Ensure Person Can Have More Than One Personal Cash Account

```java
@Test
    void ensurePersonCanHaveMoreThanOnePersonalCashAccount() {
            //arrange
            double initialAmount = 30;
            int currency = 1;
            int otherCurrency = 2;
            String personId = "joaobonifacio@hotmail.com";
            HttpStatus expectedStatus = HttpStatus.CREATED;

            //first personal cash account
            String description = "Personal cash account.";
            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
            long accountId = 10;
            Currency outputCurrency = Currency.EUR;
            AccountOutputDTO accountOutputDTO = new AccountOutputDTO(accountId, initialAmount, outputCurrency.name(), description);

            when(personalAccountService.createPersonalCashAccount(inputDTO,personId)).thenReturn(accountOutputDTO);
            cashAccountController.createPersonalCashAccount(inputDTO, personId);

            //second personal cash account
            String otherAccountDescription = "Personal american cash account.";
            AccountInputDTO inputDTOOtherAccount = new AccountInputDTO(initialAmount, otherCurrency, otherAccountDescription);
            long otherAccountId = 11;
            Currency otherOutputCurrency  = Currency.USD;
            AccountOutputDTO otherAccountOutputDTO = new AccountOutputDTO(otherAccountId, initialAmount, otherOutputCurrency.name(), otherAccountDescription);

            when(personalAccountService.createPersonalCashAccount(inputDTOOtherAccount,personId)).thenReturn(otherAccountOutputDTO);

            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTOOtherAccount, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
        }
```

**Test 3:** Fail to create a Personal Cash Account When the Initial Amount is not valid

```java
 @Test
    void ensurePersonalCashAccountNotCreatedWithInvalidAmount() {
            //arrange
            String expectedMessage = "The initial cash amount cannot be negative.";
            String description = "Personal cash account.";
            double initialAmount = -30;
            int currency = 1;
            String personId = "joaobonifacio@hotmail.com";
            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
            HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

            when(personalAccountService.createPersonalCashAccount(inputDTO,personId)).thenThrow(new InvalidAmountException("The initial cash amount cannot be negative."));


            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
        }
``` 

**Test 4:** Fail to create a personal cash account when the description is not valid

```java
@ParameterizedTest
@NullAndEmptySource
    void ensurePersonalCashAccountNotCreatedWithInvalidDescription(String candidate) {
            //arrange
            String expectedMessage = "Invalid description.";
            double initialAmount = 20;
            int currency = 1;
            String personId = "joaobonifacio@hotmail.com";
            HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, candidate);
            when(personalAccountService.createPersonalCashAccount(inputDTO,personId)).thenThrow(new InvalidAmountException("Invalid description."));

            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
        }
```

**Test 5:** Fail to create a personal cash account when the person does not exist 

```java
@Test
    void ensurePersonalCashAccountNotCreatedWhenPersonDoesNotExist() {
            //arrange
            String expectedMessage = "Person does not exist.";
            String description = "Personal cash account.";
            double initialAmount = 30;
            int currency = 1;
            String personId = "roberto_carlos@gmail.com";
            HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
            when(personalAccountService.createPersonalCashAccount(inputDTO,personId)).thenThrow(new ObjectDoesNotExistException("Person does not exist."));

            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
        }
```

### 3.4.1 Integration Tests


**Test 1:** Create a Personal Cash Account Successfully

```java
 @Test
    void ensurePersonalCashAccountIsCreatedSuccessfully() {
            //arrange
            String description = "Personal cash account.";
            double initialAmount = 30;
            int currency = 1;
            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
            String personId = familyOutputDTO.getAdminId();
            HttpStatus expectedStatus = HttpStatus.CREATED;

            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
        }
```    

**Test 2:** Ensure Person Can Have More Than One Personal Cash Account

```java
@Test
    void ensurePersonCanHaveMoreThanOnePersonalCashAccount() {
            //arrange
            double initialAmount = 30;
            int currency = 1;
            String personId = familyOutputDTO.getAdminId();

            //first personal cash account
            String description = "Personal cash account.";
            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);

            //second personal cash account
            int otherCurrency = 2;
            String otherAccountDescription = "Personal american cash account.";
            AccountInputDTO inputDTOOtherAccount = new AccountInputDTO(initialAmount, otherCurrency, otherAccountDescription);

            HttpStatus expectedStatus = HttpStatus.CREATED;

            //act
            cashAccountController.createPersonalCashAccount(inputDTO, personId);
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTOOtherAccount, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedStatus, result.getStatusCode());
        }
```

**Test 3:** Fail to create a Personal Cash Account When the Initial Amount is not valid

```java
@Test
    void ensurePersonalCashAccountNotCreatedWithInvalidAmount() {
            //arrange
            String expectedMessage = "The initial cash amount cannot be negative.";
            String description = "Personal cash account.";
            double initialAmount = -30;
            int currency = 1;
            String personId = familyOutputDTO.getAdminId();
            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);
            HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
        }
``` 

**Test 4:** Fail to create a personal cash account when the description is not valid

```java
@ParameterizedTest
@NullAndEmptySource
    void ensurePersonalCashAccountNotCreatedWithInvalidDescription(String candidate) {
            //arrange
            String expectedMessage = "Invalid description.";
            double initialAmount = 20;
            int currency = 1;
            String personId = familyOutputDTO.getAdminId();
            HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, candidate);

            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
        }
```

**Test 5:** Fail to create a personal cash account when the person does not exist

```java
@Test
    void ensurePersonalCashAccountNotCreatedWhenPersonDoesNotExist() {
            //arrange
            String expectedMessage = "Person does not exist.";
            String description = "Personal cash account.";
            double initialAmount = 30;
            int currency = 1;
            String personId = "roberto_carlos@gmail.com";
            HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;

            AccountInputDTO inputDTO = new AccountInputDTO(initialAmount, currency, description);

            //act
            ResponseEntity<Object> result = cashAccountController.createPersonalCashAccount(inputDTO, personId);

        //assert
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(expectedMessage, result.getBody().toString());
        assertEquals(expectedStatus, result.getStatusCode());
        }
```




# 4. Implementation

# 5. Integration/Demonstration

This User Story will be necessary for US180, US135, US181, US185, without it, a payment, a transfer or checking the balance will not be possible. When testing the functionality developed for , this functionality will also be indirectly tested.

At the moment, no other user stories are dependent on this one, so its integration with other functionalities cannot be tested further.

# 6. Observations

[US101]: US101_Add_Family_Member.md