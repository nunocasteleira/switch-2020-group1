US170 Create a personal cash account
=======================================

# 1. Requirements

*As a family member, I want to create a personal cash account.*

According to this requirement, the user would like to create a personal cash account. Therefore, the required fields given from the project proposal, a cash account must have:

- Account unique identification number,
- The person it belongs to,
- Its cash amount.

## 1.1 System Sequence Diagram

The below System Sequence Diagram (SSD) captures the interaction between the
Family Member and the Application when the Family Member wants to create a personal cash account.

````puml
@startuml
Header SSD
title Create a personal cash account
autonumber

actor "Family Member" as FM
participant ": Application" as app

FM -> app : Create personal cash account
activate FM
activate app
app --> FM : ask initial amount
deactivate app

FM -> app : inputs data
activate app
app --> FM: informs result
deactivate app
deactivate FM
@enduml
````

## 1.2 Dependency of other User Stories

This User Story has dependencies with the [US101], since it
will be necessary to have a member, so we can create a personal cash account.

# 2. Analysis

## 2.1 Cash Account entry

According to what was presented, a personal cash account is created upon request from the Family Member.

A personal cash account instance should have the following attributes:

| Attributes | Rules                                        |
| ---------- | -------------------------------------------- |
| accountId  | Unique, required, UUID |
| owner   | Required, object                            |
| cashAmount | Required, double                            |

The default cashAmount value is zero.

## 2.2 Domain Model Excerpt

The relevant extract of the domain model is represented below:

```plantuml
@startuml
hide empty members

object PersonalCashAccount {
- accountId
- accountOwner
- balance
- designation

}

object Person {
- personId
- name
- birthDate
- vatNumber
- address
- emailAddressList
- phone
  }

PersonalCashAccount "0...*" - "1" Person

@enduml
```

# 3. Design

## 3.1. Functionality Development

Regarding the creation of a personal account, we should accommodate the requirements specified in [Analysis](#2-analysis).

The System Diagram is the following:

```puml

@startuml

header SD
title Add a personal cash account
autonumber
actor "Family Member" as FM
participant ": UI" as UI
participant ": CreatePersonal\nCashAccount\nController" as ctrl
participant ": FFM\nApplication" as app
participant ": AccountService" as CS

activate FM

FM -> UI : Create personal cash account
activate UI
UI --> FM : Ask initial Amount
deactivate UI

FM -> UI : inputs data
activate UI
UI -> ctrl : createPersonal\nCashAccount(amount, currency)
activate ctrl

ctrl -> app : getLoggedPerson()
activate app
app --> ctrl : person
deactivate app

ctrl -> app : getAccountService()
activate app
app --> ctrl : AccountService
deactivate app

ctrl -> CS : createPersonalCashAccount(person, amount, currency)
activate CS
  ref over CS
  createPersonalCashAccount()
  end ref
CS --> ctrl : aPersonalCashAccount
deactivate CS
ctrl --> UI : inform result
deactivate ctrl
UI --> FM : inform result
deactivate UI
deactivate FM

```

```puml

@startuml
autonumber
header ref
title createFamilyCashAccount()
participant ": AccountService" as CS

[-> CS : createPersonalCashAccount(person, amount, currency)
activate CS
CS --> OP as "ownerPerson \n: AccountOwner<Person>"** : create(person)
CS --> MV as "montaryValue \n: MonetaryValue"** : create(currency, amount)
CS --> PCS as "aPersonalCashAccount \n: PersonalCashAccount"** : create(ownerperson, monetaryValue)

CS -> CS : add(aPersonalCashAccount)


[<-- CS : aPersonalCashAccount
deactivate CS 

@enduml
```

## 3.2. Class Diagram

The main classes involved in the realization of this requirement/functionality were the following:

- CreatePersonalCashAccountController
- AccountService
- Person
- PersonalCashAccount

The Class Diagram is the following:

```plantuml
@startuml
title Class Diagram US170
hide empty members
skinparam linetype ortho

class CreatePersonalCashAccountController{
 + createPersonalCashAccount()
}

Application -> CreatePersonalCashAccountController : accountService

class Application{
+ getLoggedPerson()
+ getAccountService()
}

class Person{
- personId
- name
- birthDate
- vatNumber
- address
- emailAddressList
- phone
}

CreatePersonalCashAccountController -.> AccountService 
CreatePersonalCashAccountController .> Person

class AccountService {
+ createPersonalCashAccount()
}

AccountService *- Account : accounts
AccountService --.> AccountOwner 
MonetaryValue <. AccountService
AccountService .> CashAccount

class AccountOwner{
-owner
-name
}

class MonetaryValue{
-currency
-amount
}

class CashAccount{
}

abstract class Account{
- accountId
- accountOwner
- balance
- currency
- transactionList
- designation
}

Account *- AccountOwner
CashAccount --|> Account
AccountOwner - Person


@enduml
```

## 3.3. Applied Patterns

The applied patterns were the following:

- *Single Responsibility Principle (SRP)*  - All classes have one
  responsibility, which means, only one reason to change;
- *Controller* - The controller (CreatePersonalCashAccountController) receives and coordinates
  system operations connecting the UI layer to the App's logic layer;
- Information Expert - Each class was assigned responsibilities that can be
  fulfilled because they have the information needed to do so;
- *Pure Fabrication* - The AccountService class is a class that does not
  represent a problem domain concept, nevertheless it was assigned a set of
  responsibilities to support high cohesion, low coupling and reuse;
- *Low Coupling* - Classes were assigned responsibilities so that coupling
  remains as low as possible, reducing the impact of any changes made to objects
  later on;
- *High Cohesion* - Classes were assigned responsibilities so that cohesion
  remains high(they are strongly related and highly focused). This helps to keep
  objects understandable and manageable, and also goes hand in hand with the low
  coupling principle.

## 3.4. Tests

### 3.4.1 Unit Tests

The Unit Tests are defined below:

**Test 1:** Creat a Personal Account Successfully
```java
@Test
void createPersonalCashAccountSuccessfully() {
boolean result = accountService.createPersonalCashAccount(person, 10);

        assertTrue(result);
}
```    
**Test 2:** Fail to create because already as one 
```java
@Test
void createPersonalCashAccountUnsuccessfully() {
    accountService.createPersonalCashAccount(person, 10);
    
    boolean result = accountService.createPersonalCashAccount(person, 10);

    assertFalse(result);
}
```

**Test 3:** Create two successful accounts
```java
@Test
void createPersonalCashAccountSuccessfullyForTwoPersons() {
    Person person1 = new Person(1, "Name", "12/12/2012", "234324534", 234235234, "sdgvzf", "4800-344", "aergb");
    accountService.createPersonalCashAccount(person1, 10);

    boolean result = accountService.createPersonalCashAccount(person, 10);

    assertTrue(result);
}
``` 

**Test 4:** Fail to create a account with no person
```java
@Test
void createPersonalCashAccountWithNoPerson() {
    boolean result = accountService.createPersonalCashAccount(null, 0);

    assertFalse(result);
}
```

# 4. Implementation

On this user story we think that the major challenge was to find a way to storage the account.

First we though that the best solution was to storage the account as a person attribute. After some discussion we got to the conclusion that maybe the best way was to create a service responsible for all accounts and to storage all created accounts at one list.

This is possible because we created a generic class for the account owner, that allow us to have either a person or a family as account owner. That way we can have all on the same list.

```puml

@startuml
autonumber
header ref
title createPersonalCashAccount()
participant ": AccountService" as CS
participant ": Person" as p

[-> CS : createPersonalCashAccount()
activate CS



CS --> PCS as "aPersonalCashAccount \n: PersonalCashAccount"** : createPersonalCashAccount()

CS -> p : createPersonalCashAccount(aPersonalCashAccount)
activate p
p -> p : existsCashAccount()
opt result == false
p -> p : addCashAccount(aPersonalCashAccount)
end

p --> CS : inform result
deactivate p

[<-- CS : inform result
deactivate CS 

@enduml
```

# 5. Integration/Demonstration


# 6. Observations


[US101]: US101_Add_Family_Member.md