# US186 Get List of Movements Between Two Dates

# 1. Requirements

_As a family member, I want to get the list of movements on one of my accounts between two dates._

The functionality will allow a family member to check the list of movements in one of his/her
accounts, between two dates specified by him/her. It was designed to respond to the User Story (US)
referred above.

## 1.1 System sequence diagram

```plantuml
@startuml

header ssd
title US105 Get List of Movements Between Two Dates
autonumber
actor "Family Member" as fm
actor ": Application" as app
activate fm
fm -> app: Request to see list of movements

activate app
app --> fm: Ask for the start and end dates
deactivate app
fm -> app: Input the two dates
activate app
app --> fm: Present list of movements
deactivate app
deactivate fm

@enduml
```

# 1.2. Dependency of other user stories

This US is dependent on [US170], [US171], [US172] and [US173], because the user would not be able to
check the movements of an account if there is no functionality that allows to add a personal account
first.

Indirectly, this US is also dependent on [US180], [US130] and [US181], because without them,
movements that include transfers from different cash accounts and payments made using a cash account
would not be shown in the movements list.

# 2. Analysis

### 2.1 Movements

By movement, it is meant a transaction of money from a place to another. At this moment,
transactions can be payments or transfers.

In the functionality developed for this US, the family member will to obtain the list of movements
that occurred on a given period of time, specified by him/her, on a given personal account. The list
of movements is, for now, stored in an abstract class Account. Each transaction has set of
attributes (timestamp, amount, postBalance, description and category), whose values will be obtained
with the list.

Certain accounts ask for an initial amount upon creation, and the transfer of this amount to the
account counts as a valid transaction that can be included in the movement list.

When choosing the time period, the start date will be mandatory to choose, but the end date will be
set to the current system date by default. The start date must always be earlier, or the same as the
end date.

### 2.2 Relevant domain model excerpt

Below is the excerpt of the domain model that is relevant for this user story.

```plantuml
@startuml
title Domain Model US186
hide methods

object Account {
  - accountId
  - accountOwner
  - currency
  - transactionList
  - designation
}

Account "1" - "0..*" Transaction : has

object Transaction{
 - transactionId
 - timestamp
 - amount
 - postBalance
 - description
 - category
 - customTimestamp
}

@enduml
```

# 3. Design

## 3.1. Functionality development

In the diagrams below, it is possible to see the implementation strategy for this user story.

### 3.1.1 Sequence diagrams

```plantuml
@startuml

header sd
title US186 Get Account Movements Between Two Dates
autonumber
actor "Family Member" as fm
participant ": UI" as ui
participant ": GetAccountMovements\nController" as controller
participant ": Application" as app
participant "transactionService:\nTransactionService" as service

activate fm
fm -> ui: get the list of movements of the\naccount on a given period of time
activate ui
ui --> fm: ask to define a start and an end date
deactivate ui
fm -> ui: select start and end date
activate ui
ui -> controller: getAccountMovementsBetweenTwoDates\n(accountId, startDate, endDate)
activate controller
controller -> app: getTransactionService()
activate app
app --> controller: transactionService
deactivate app
|||
controller -> service: getAccountMovementsBetweenTwoDates\n(accountId, startDate, endDate)

activate service
ref over service
    getAccountMovementsBetweenTwoDates()
end ref
service --> controller: accountMovementListDTO
deactivate service
controller --> ui: accountMovementListDTO
deactivate controller
ui --> fm: present list of account movements
deactivate ui
deactivate fm
@enduml
```

```plantuml
@startuml

header ref
title getAccountMovementsBetweenTwoDates()
autonumber

participant "transactionService\n: TransactionService" as service
participant "accountMovementsMapper\n: AccountMovements\nMapper" as mapper
participant "accountMovementsDTO\n: Account\nMovementsDTO" as dto
participant "account \n: Account" as account
participant "transaction \n : Transaction" as transaction
participant "movementsBetweenTwoDates\n: List<Transaction>" as movementsBetweenTwoDates
participant "anAccountMovementDTOsList\n: List<AccountMovementDTO>" as dtoList

-> service: getAccountMovementsBetweenTwoDates\n(accountId, startDate, endDate)
activate service
service -> account: getTransactionList()
activate account
account --> service: transactionList
deactivate account
service --> movementsBetweenTwoDates**: create
|||
loop for each transaction in transactionsList
   service -> transaction: getTimestamp();
   activate transaction
   transaction --> service: transactionTimestamp
   |||
   service -> transaction: result = checkIfDateBelongsInTimeInterval(transactionTimeStamp, startDate, endDate)
   transaction --> service: boolean result
   deactivate transaction
   |||
      opt result == true
        service -> movementsBetweenTwoDates: add(transaction)
        activate movementsBetweenTwoDates
        deactivate movementsBetweenTwoDates
      end

end
|||
service --> mapper**: createMapper()
service -> mapper: mapAccountMovementList\n(movementListBetweenTwoDates)
activate mapper
mapper --> dtoList**: create()
loop for each movement in the movement list
  mapper -> transaction: getTransactionInfo()
  activate transaction
  transaction -> mapper: transactionInfo
  deactivate transaction
  mapper -> mapper: mapAccountMovement(transaction)
  mapper -> dtoList: add(accountMovementDTO)
  activate dtoList
  deactivate dtoList
end
mapper --> dto**: createAccountMovementListDTO\n(anAccountMovementList)
mapper --> service: accountMovementListDTO
deactivate mapper
<-- service: accountMovementListDTO

@enduml
```

## 3.2. Class Diagram

```plantuml

@startuml
title Class Diagram US186

class GetAccountMovementsController{
- application : Application
+getAccountMovementsBetweenTwoDates(accountId, startDate, endDate)
}

GetAccountMovementsController "1" -- "1" Application: belongsTo >

class Application{
- transactionService: TransactionService
+getTransactionService()
}

Application "1" - "1" TransactionService : calls >


class TransactionService{
- accountService: AccountService
+ getAccountMovementsBetweenTwoDates(accountId, startDate, endDate)
}

TransactionService "1" -- "1" Account : calls >

class Account{
- transactionList: List<Transaction>
+getTransactionList()
}

Account "1" -- "0*" Transaction : calls >

class Transaction{
- transactionId: UUID
- timestamp: Date
- amount: MonetaryValue
- postBalance MonetaryValue
- description: String
- category: BaseCategory
- customTimeStamp: Date
+getDescription()
+getCategory()
+getTimestamp()
+getAmount()
+getPostBalance()
}

TransactionService "1" - "1" AccountMovementsMapper : creates >

class AccountMovementsMapper{
+mapAccountMovementList(transactionList)
-mapAccountMovement(transaction)
}

AccountMovementsMapper "1" -- "1" AccountMovementListDTO : creates >

class AccountMovementListDTO{
- accountMovementsBetweenTwoDates: List<AccountMovementDTO>
+ getAccountMovementsDTOList()
}

AccountMovementListDTO "1" -- "0*" AccountMovementDTO : contains >


class AccountMovementDTO{
- description: String
- category: BaseCategory
- amount: MonetaryValue
- postBalance: MonetaryValue
- timeStamp: Date
- customTimeStamp: Date
}

@enduml
```

## 3.3. Applied patterns and principles

- _Single Responsibility Principle (SRP)_ - Transaction and Account classes follow this principle,
  as they have one responsibility, which is to manage the information included within them.
- _Open-Closed Principle_ - This functionality was designed to accept extension without having to
  add changes to the methods already implemented. If more information is to be presented about each
  transaction or the account, this can be easily added.
- _Dependency inversion principle_ - This functionality depends on the abstract classes Account and
  Transaction, and not on the concrete classes that implement these.
- _Controller_ - GetAccountMovementsController receives and coordinates system operations, as it
  connects the UI layer to the application logic layer.
- _Information Expert_ - To each class were assigned responsibilities that can be fulfilled because
  they have the information needed to do so;
- _Creator_ - The TransactionService class was assigned the responsibility to instantiate the
  mapper (class that compiles all the information needed to create the DTO), because it had the
  necessary data that would be passed on to it.
- _Pure Fabrication_ - The TransactionService and the AccountMovementsMapper classes are classes
  that do not represent a domain concept, and they were assigned a set of responsibilities to
  support high cohesion, low coupling and reuse.
- _Low Coupling_ - Classes were assigned responsibilities so that coupling remains as low as
  possible, reducing the impact of any changes made to objects later on;
- _High Cohesion_ - Classes were assigned responsibilities so that cohesion remains high (they are
  strongly related and highly focused). This helps to keep objects understandable and manageable,
  and also goes hand in hand with the low coupling principle.

## 3.4. Domain tests

### 3.4.1. Unit testing

**Unit test 1:** Verify that no movements outside the specified time interval are included in the
list

```java

@Test
void getAccountMovementListDTOSuccessfully_DatesBetweenTomorrowAndAfterTomorrow(){
        //Arrange
        UUID personalCashAccountId=personalCashAccount.getAccountId();
        List<Transaction> transactionList=personalCashAccount.getTransactionList();
        Calendar today=Calendar.getInstance();
        Calendar tomorrow=Calendar.getInstance();
        tomorrow.add(Calendar.DATE,1);
        Calendar afterTomorrow=Calendar.getInstance();
        afterTomorrow.add(Calendar.DATE,2);

        transactionList.get(0).setCustomTimestamp(today.getTime());
        transactionList.get(1).setCustomTimestamp(tomorrow.getTime());
        transactionList.get(2).setCustomTimestamp(afterTomorrow.getTime());

        GetAccountMovementsController controller=new GetAccountMovementsController(app);
        int expectedListSize=2;

        //Act
        AccountMovementListDTO accountMovementListDTO=controller.getAccountMovementsBetweenTwoDates(personalCashAccountId,tomorrow.getTime(),afterTomorrow.getTime());
        List<AccountMovementDTO> accountMovementDTOList=accountMovementListDTO.getAccountMovementsDTOList();
        int resultListSize=accountMovementDTOList.size();

        //Assert
        assertEquals(expectedListSize,resultListSize);
        assertNotNull(accountMovementDTOList);
        assertNotNull(accountMovementListDTO);
        }

```

**Unit test 2:** Verify that, if no transactions were registered in the specified dates, an empty
list of movements is returned.

```java

@Test
void getEmptyAccountMovementListDTO_DatesBetweenTomorrowAndAfterTomorrow(){
        //Arrange
        UUID personalCashAccountId=personalCashAccount.getAccountId();
        List<Transaction> transactionList=personalCashAccount.getTransactionList();
        Calendar today=Calendar.getInstance();
        Calendar tomorrow=Calendar.getInstance();
        tomorrow.add(Calendar.DATE,1);
        Calendar afterTomorrow=Calendar.getInstance();
        afterTomorrow.add(Calendar.DATE,2);

        transactionList.get(0).setCustomTimestamp(today.getTime());
        transactionList.get(1).setCustomTimestamp(today.getTime());
        transactionList.get(2).setCustomTimestamp(today.getTime());

        GetAccountMovementsController controller=new GetAccountMovementsController(app);
        int expectedListSize=0;

        //Act
        AccountMovementListDTO accountMovementListDTO=controller.getAccountMovementsBetweenTwoDates(personalCashAccountId,tomorrow.getTime(),afterTomorrow.getTime());
        List<AccountMovementDTO> accountMovementDTOList=accountMovementListDTO.getAccountMovementsDTOList();
        int resultListSize=accountMovementDTOList.size();

        //Assert
        assertEquals(expectedListSize,resultListSize);
        assertNotNull(accountMovementDTOList);
        assertNotNull(accountMovementListDTO);
        }

```

**Unit test 3:** Verify that if the end date is not specified, the current system date is adopted.

```java

@Test
void getAccountMovementListDTOSuccessfully_EndDateIsNullAndSetToDefault(){
        //Arrange
        UUID personalCashAccountId=personalCashAccount.getAccountId();
        List<Transaction> transactionList=personalCashAccount.getTransactionList();
        Calendar today=Calendar.getInstance();
        Calendar yesterday=Calendar.getInstance();
        yesterday.add(Calendar.DATE,-1);
        Calendar tomorrow=Calendar.getInstance();
        tomorrow.add(Calendar.DATE,1);

        transactionList.get(0).setCustomTimestamp(today.getTime());
        transactionList.get(1).setCustomTimestamp(yesterday.getTime());
        transactionList.get(2).setCustomTimestamp(tomorrow.getTime());

        GetAccountMovementsController controller=new GetAccountMovementsController(app);
        int expectedListSize=3;

        //Act
        AccountMovementListDTO accountMovementListDTO=controller.getAccountMovementsBetweenTwoDates(personalCashAccountId,yesterday.getTime(),null);
        List<AccountMovementDTO> accountMovementDTOList=accountMovementListDTO.getAccountMovementsDTOList();
        int resultListSize=accountMovementDTOList.size();
        //Assert
        assertEquals(expectedListSize,resultListSize);
        assertNotNull(accountMovementDTOList);
        assertNotNull(accountMovementListDTO);
        }

```

# 4. Implementation

The main challenge on implementing this functionality was finding a solution on how to present the
necessary data to the end user without exposing information from domain classes. The answer to this
was creating a DTO for each account transaction and its information and a DTO for the list of the
transaction DTOs. This solution allows the addition of other information to the DTOs if needed in
the future, without having to change the implementation.

# 5. Integration/Demonstration

As mentioned before, this US is dependent
on [US180], [US130] and [US181], because without them, movements that
include transfers from different cash accounts and payments made using a cash account would not be
shown in the movements list. Because of this dependence and by testing this functionality, it is
possible to test its integration with the USs mentioned above.

# 6. Observations

[US130]: US130_Transfer_Money_From_Family_Cash_Account_To_Family_Member_Cash_Account.md

[US170]: US170_Create_Personal_Cash_Account.md

[US171]: US171_Add_Bank_Account.md

[US172]: US172_Add_Bank_Savings_Account.md

[US173]: US173_Add_Credit_Card_Account.md

[US180]: US180_Transfer_Money_From_Cash_Account_To_Another_Cash_Account.md

[US181]: US181_Register_Payment_Made_Using_A_Cash_Account.md