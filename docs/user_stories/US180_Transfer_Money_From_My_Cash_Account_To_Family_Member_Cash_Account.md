US180 Transfer Money From My Cash Account To Family Member Cash Account
=======================================

# 1. Requirements

*As a family member, I want to transfer money from my cash account to another family member’s cash account.*

From the project proposal: *"Each family member may have a cash account to represent the amount of cash (s)he has."*, so the family member can have one or none cash account.

This functionality will allow each family member with cash account to transfer money from your own cash account to other family member's cash account. It was designed to fulfill to the user story/requirement
above.

The development team designed this functionality to allow money transfer only when both family members have a cash account. Also, the balance of the family member's origin cash account (account were the money will come from) must be greater than the amount of money to be transferred.

Transferring money from the family member´s cash account to another family member´s cash account will only be successful if these criteria are fulfilled.

## 1.1. System Sequence Diagram

The System Sequence Diagram below represents the communication between a Family Member and the Application.

```puml
@startuml

header SSD
autonumber
title Transfer Money From My Cash Account\nTo Family Member Cash Account
actor "Family Member" as FM
participant ": System" as S

FM -> S : Transfer money to family member
activate S
activate FM
S --> FM : display list of family members
deactivate S

FM -> S: selects family member
activate S
S --> FM : display list of family members' accounts
deactivate S

FM -> S: selects account
activate S
S --> FM : asks transfer information and list of categories
deactivate S

FM -> S : inputs data and selects category
activate S
S --> FM : informs result
deactivate S
deactivate FM

@enduml
```

## 1.2. Dependency of other user stories`

This user story is dependent on [US010](US010_Create_Family.md) and [US101](US101_Add_Family_Member.md) because without having a family and family members, it would be impossible to get the list of family members and to create their cash account.

In addition, this user story is dependent on [170](US170_Create_Personal_Cash_Account.md) because it will be necessary to have a family member's cash account.


# 2. Analysis

## 2.1. Transaction Entry

The transfer of money between the family member's cash account and the other family member's cash account implies the need to have a transaction.

The transaction object, will have the following attributes:

Attributes      | Rules
----------------|----------------
**transactionId**     | unique, required, UUID, random
**timestamp**         | required, Date
**amount**           | required, double
**currency**          | required, String
**postBalance**       | required, MonetaryValue
**description**     | alphanumeric, String
**category**          | alphanumeric, String
**customTimeStamp**   | optional, Date
**otherTransfer**     | Transfer
**otherAccountOwner** | required, accountOwnerDTO

## 2.2. Relevant domain model excerpt

The excerpt of the Domain Model that is relevant for this user story is as follows:

```plantuml
@startuml

title Domain Model Excerpt US180
hide empty members

object Person {
- personId
- name
- birthDate
- phone
- email
- vat
- adress
- emailAdressList
}

object AccountId {
    - accountIdNumber
}

object Transaction {
  - description
  - amount
  - date
  - category
}

object Transfer {
}


AccountId - Transfer : has destinationAccountId <
AccountId - Transfer : has originAccountId <
Transfer --|> Transaction
Person "1" -- "0..*" AccountId : has >

@enduml
```


# 3. Design

## 3.1. Functionality Realization

The Sequence Diagram for realizing the functionality of this user story is as follows:

```plantuml
@startuml

header SD
title Get list of family members
autonumber
actor "Family Member" as FM
participant ": UI" as UI
participant ": FamilyController" as FC
participant "familyMemberService\n: FamilyMemberService" as FMS
participant "familyMembersDTO\n: FamilyMembersOutputDTO" as DTO
participant "familyRepository\n: FamilyRepository" as FR
participant "family\n: Family" as F

FM -> UI : transfer money
activate FM
activate UI

UI -> FC : getFamilyMembers(familyId)
activate FC

FC -> FMS : getFamilyMembers(familyId)
activate FMS

FMS -> FR : getFamilyMembers(familyId)
activate FR

FR -> FR : family = getByFamilyId(familyId)
FR -> F : getFamilyMembers()
activate F

F --> FR : familyMembers
deactivate F

FR --> FMS : familyMembers
deactivate FR

FMS -> DTO* : create(familyMembers)
activate DTO

DTO --> FMS : familyMembersDTO
deactivate DTO

FMS --> FC : familyMembersDTO
deactivate FMS

FC --> UI : familyMembersDTO
deactivate FC

UI --> FM : presents list of family members
deactivate FM

@enduml
```

```plantuml
@startuml

header SD
title Get family member's cash account list
autonumber
actor "Family Member" as FM
participant ": UI" as UI
participant ": PersonController" as PC
participant "personAccountService\n: PersonAccountService" as PAS
participant "email\n: Email" as E
participant "personRepository\n: PersonRepository" as PR
participant "person\n: Person" as P
participant "accountRepository\n: AccountRepository" as AR
participant "cashAccountsJPA\n: List<CashAccountJPA>" as L
participant "iAccountRepositoryJPA\n: IAccountRepositoryJPA" as JPA
participant "accountDomainDataAssembler\n: AccountDomainDataAssembler" as ADDA
participant "cashAccountsDTO\n: CashAccountsOutputDTO" as DTO

FM -> UI : selects family member
activate FM
activate UI

UI -> PC : getCashAccounts(personId)
activate PC

PC -> PAS : getCashAccounts(personId)
activate PAS

PAS --> E* : create(personId)
activate E

E --> PAS : aPersonId
deactivate E

PAS -> PR : getCashAccounts(aPersonId)
activate PR

PR -> PR : person = getByEmail(aPersonId)
PR -> P : getPersonalAccounts()
activate P

P --> PR : accounts
deactivate P

PR --> PAS : accounts
deactivate PR

PAS -> AR : getByAccountId(accounts)
activate AR

AR -> AR : longAccounts = fromAccountIdToLong(accounts)

AR --> L* : create()

loop for each longAccountId in longAccounts
AR -> JPA : getByAccountId(longAccountId)
activate JPA
JPA --> AR : cashAccountJPAOptional
deactivate JPA

alt ifPresent
AR -> L : add(cashAccountJPAOptional)
activate L
deactivate L
end
end

AR -> ADDA : cashAccountsJPAToDomain(cashAccountsJPA)
activate ADDA

ADDA --> AR : cashAccounts
deactivate ADDA

AR --> PAS : cashAccounts
deactivate AR

PAS -> DTO* : create(cashAccounts)
activate DTO

DTO --> PAS : cashAccountsDTO
deactivate DTO

PAS --> PC : cashAccountsDTO
deactivate PAS

PC --> UI : cashAccountsDTO
deactivate PC

UI --> FM : presents list of cash accounts
deactivate UI
deactivate FM

@enduml
```

```plantuml
@startuml

header SD
title Transfer Money From My Cash Account\nTo Family Member Cash Account
autonumber
actor "Family Member" as FM
participant ": UI" as UI
participant ": TransactionController" as TC
participant "transactionService\n: TransactionService" as TS
participant "transactionAssembler\n: TransactionAssembler" as TA
participant "transferFactory \n: TransferFactory" as TF
participant "transactionRepository \n: TransactionRepository" as TR

note over FM
The first interactions 
are detailed in the above 
sequence diagrams
end note
FM -> UI : inputs data
activate FM
activate UI
UI -> TC : transfer(transferInputDTO, originAccountId, destinationAccountId)
activate TC
TC -> TS : transfer(transferInputDTO, originAccountId, destinationAccountId)
activate TS
TS -> TA : toDomain(transferInputDTO, originAccountId, destinationAccountId)
activate TA
TA -> TS : transferVOs
deactivate TA

TS -> TF : buildTransfer(transferVOs)
activate TF
ref over TF
create()
end
return aTransfer

TS -> TR : save(aTransfer)
activate TR
ref over TR
  save()
end ref
return : transferOutputDTO
deactivate TR

TS --> TC : transferOutputDTO
deactivate TS

TC --> UI : transferOutputDTO
deactivate TC

UI --> FM : presents transfer details \nand informs success

@enduml
```

```puml
@startuml

header ref
title create()
autonumber
participant "transferFactory\n: TransferFactory" as TF
participant "transferBuilder\n: TransferBuilder" as TB
participant "description\n: Description" as D
participant "date\n: Date" as Date
participant "category\n: Category" as C
participant "transfer\n: Transfer" as T

[-> TF : buildTransfer(transferVOs)
activate TF
TF --> TB* : buildTransfer(transferVOs.getOriginAccountId, \ntransferVOs.getDestinationAccountId, transferVOs.getAmount())
activate TB
TB --> D* : withDescription()
TB --> Date* : withDate()
TB --> C* : withCategory()

TB --> T* : create(transferBuilder)
TB--> TF : aTransfer
deactivate TB

<-- TF : aTransfer
deactivate TF

@enduml
```

```puml
@startuml

header ref
title save()
autonumber
participant "transactionRepository\n: TransactionRepository" as TR
participant "transactionDomainDataAssembler\n: TransactionDomainDataAssembler" as TA
participant "aTransferJPA\n:TransferJPA" as TJPA
participant "iTransactionRepositoryJPA\n: ITransactionRepository" as ITR

-> TR : save(aTransfer)
activate TR

TR -> TA : toData(aTransfer)
activate TA
TA --> TJPA** : create(transferId, originAcountIdJPA, destinationAcountIdJPA,\namountJPA, descriptionJPA, dateJPA)
TA --> TR : aPaymentJPA
deactivate TA

TR -> ITR : save(aPaymentJPA)
activate ITR
ITR --> TR : savedPaymentJPA
deactivate ITR

<-- TR : savedPaymentJPA
deactivate TR

```

## 3.2. Class Diagram

The Class Diagram of the functionality of this user story is as follows:

```plantuml
@startuml

title Class Diagram US180

class TransferMyCashToMemberController {
- application : Application
+ getListOfAccountOwnersInFamily()
+ transferMyCashToMember(accountOwner, currency, value)
}

TransferMyCashToMemberController "1"-"1" Application

class Application {
- accountService : AccountService
- transactionService : TransactionService
- loggedPerson
+ getAccountService()
+ getTransactionService()
}

Application "1"-"1" AccountService : calls

class AccountService {
- accountList : List<Account>
+ getListOfOnlyAccountOwnersDTO(family)
# hasCashAccount(familyMember)
- isCashAccount(account)
+ equals(owner)
+ getCashAccountByOwner(familyMember)
}

AccountService "1"-"1" AccountOwnerMapper : creates

class Family {
- personList : List<Person>
+ getFamilyMembersList()
}

class AccountOwnerMapper {
- accountOwner : Ownership
- account : Account
+ toDTO()
}

AccountOwnerMapper "1"-"1" AccountOwnerDTO : creates

class AccountOwnerDTO {
- name : String
- account : Account
+ getAccount()
}

AccountOwnerDTO "1"-"1" Person
AccountOwnerDTO "1"--"1" Account

class Person {
- name : String
}

Person -|> Ownership

interface Ownership {
getOwnerName()
}

abstract class Account {
# accountId : UUID
# accountOwner : AccoutOwner
# currency : Currency
# designation : String
+ getAccountOwner()
}

Application "1"--"1" TransactionService : calls

class TransactionService {
- accountService : AccountService
+ transferMyCashToMember(familyMember, familyMemberAccountOwnerDTO, currency, value)
- moveAmountBetweenAccounts(personalCashAccountOrigin, personalCashAccountDestination, moneyAmount)
}

Transaction "1"-"1" MonetaryValue : has

class MonetaryValue {
- value : BigDecimal
- currency : Currency
+ createMonetaryValue(currencyString, doubleValue)
}

MonetaryValue "1"-"1" Currency : has

class Currency {
+ getInstance(currencyString)
}

interface Transferable {
hasSameCurrency(destinationAccount)
transferTo(movementAmount, destinationAccount)
transferFrom(movementAmount, originAccount)
}

TransactionService "1"--"1" Transfer

class Transfer {
- otherTransfer : Transfer
- otherAccountOwner : String
+ setOtherTransaction(credit)
+ setOtherTransaction(debit)
}

Transfer "1"--|>"1" Transaction

abstract class Transaction {
- transactionId : UUID
- timestamp : Date
- amount : MonetaryValue
- postBalance : MonetaryValue
- description : String
- category : BaseCategory
- customTimestamp : Date
+ getDescription()
+ getCategory()
+ getTimestamp()
+ getAmount()
+ getPostBalance()
}

Transfer "0..*"-"1" PersonalCashAccount : has

class PersonalCashAccount {
}

PersonalCashAccount "1"-|>"1" Account
class PersonalCashAccount implements Transferable

@enduml
```

## 3.3. Applied Patterns

- *Single Responsibility Principle (SRP)* - All classes involved in this user story follow this principle and this means that they have only one and well-defined responsibility, which is to manage the information included within them.

- *Controller* - The TransferMyCashToMemberController receives and coordinates system operations, as it connects the UI layer to the Application logic layer.

- *Information Expert* - Each class was assigned responsibilities that can be fulfilled because they have the information needed and where that information stored.

- *Pure Fabrication* - The AccountService and TransactionService classes are classes that does not represent a domain concept, and it was assigned a set of responsibilities to support high cohesion, low coupling, and the potential for a reuse.

- *Low Coupling* - Classes were assigned responsibilities so that coupling remains as low as possible, reducing the impact of any changes made to objects later on. The implementation of AccountService and TransationService classes reduced the dependency level between them.

- *High Cohesion* - Classes were assigned responsibilities so that cohesion remains as high as possible, to keep objects understandable and manageable. They are strongly related and highly focused. Like the low coupling principle, the AccountService and TransationService classes increased the level of cohesion between them.

## 3.4. Tests

Below is the list of unit tests:

- **Test 1:** Obtain a valid DTO with a list of family members with personal cash account.

```java
    @Test
    void getAccountOwnersInFamily_Successfully_TwoPerson() {
            //arrange
            List<AccountOwnerDTO> expectedList = new ArrayList<>();
        double initialAmount = 500;
        accountService.createPersonalCashAccount(Maria, initialAmount, "EUR");
        accountService.createPersonalCashAccount(Rita, initialAmount, "EUR");
        personalAccountMaria = accountService.getCashAccountByOwner(Maria);
        personalAccountRita = accountService.getCashAccountByOwner(Rita);
        AccountOwnerMapper accountOwnerMapperMaria = new AccountOwnerMapper(Maria, personalAccountMaria);
        AccountOwnerMapper accountOwnerMapperRita = new AccountOwnerMapper(Rita, personalAccountRita);

        //act
        expectedList.add(accountOwnerMapperMaria.toDTO());
        expectedList.add(accountOwnerMapperRita.toDTO());
        List<AccountOwnerDTO> result = controller.getListOfAccountOwnersInFamily();

        //assert
        assertEquals(expectedList, result);
    }
```

- **Test 2:** Ensure that a transfer is successful.

```java
    @Test
    void transferMyCashToMember_Successfully() {
        //arrange
        double initialAmount = 500;
        accountService.createPersonalCashAccount(Maria, initialAmount, "EUR");
        String currency = "EUR";
        double value = 50.5;

        //act
        boolean result = controller.transferMyCashToMember(accountOwnerRita, currency, value);

        //assert
        assertTrue(result);
    }
```

- **Test 3:** Ensure that a transfer is unsuccessful, when the family member's origin cash account have the insufficient money amount.

```java
    @Test
    void transferMyCashToMember_Unsuccessfully_InsufficientMoneyAmount() {
            //arrange
            double initialAmount = 100;
            accountService.createPersonalCashAccount(Maria, initialAmount, "EUR");
            String currency = "EUR";
            double value = 200;

            //act
            boolean result = controller.transferMyCashToMember(accountOwnerRita, currency, value);

            //assert
            assertFalse(result);
    }
```

- **Test 4:** Ensure that a transfer is unsuccessful, when the amount to transfer is negative.

```java
    @Test
    void transferMyCashToMember_Unsuccessfully_NegativeMoneyAmount() {
            //arrange
            double initialAmount = 100;
            accountService.createPersonalCashAccount(Maria, initialAmount, "EUR");
            String currency = "EUR";
            double value = -50;

            //act && assert
            assertThrows(InvalidAmountException.class, () -> controller.transferMyCashToMember(accountOwnerRita, currency, value));
    }
```


# 4. Implementation

The main challenge that were found while implementing this user story was to integrate classes related to accounts, namely cash account, with the functionality of this user story, which was transferring money between family member's cash account.

To minimize these difficulties, a lot of research and study of reliable documentation was done. There was communication with the Product Owner whenever
needed, to clarify some requirements.

So that we could present a reliable functionality, many tests were done, to identify as many possible errors in the implementation as possible.


# 5. Integration/Demonstration

This user story will be indirectly necessary for [US186](US186_Get_Movements_Between_Two_Dates.md) because, without it, the correspondent movement would not exist in the list to be retrieved. When testing the functionality developed for the user story mentioned above, this functionality will also be indirectly tested.

At the moment, no other user stories are dependent on this one, so its integration with other functionalities cannot be tested further.


# 6. Observations

It was implemented a DTO, i.e, a AccountOwnerDTO class with the purpose to display the list of a family members with cash account in a family.