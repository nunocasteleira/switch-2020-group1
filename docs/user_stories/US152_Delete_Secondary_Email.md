US152 Delete a Secondary Email From Family Member Profile
=======================================

# 1. Requirements

*As a family member, I want to delete a secondary email from my profile.*

According to this requirement, the user would like to remove a secondary email from its own profile. 
Therefore, in order to remove the email, the secondary email should exist first and foremost.

## 1.1 System Sequence Diagram

The below System Sequence Diagram (SSD) captures the interaction between the Family Member and the
Application when the Family Member wants to delete a secondary email from his profile.

````puml
@startuml
Header SSD
title Delete a Secondary Email From Family Member Profile
autonumber

actor "Family Member" as FM

FM -> ": Application" : Remove secondary email
activate FM
activate ": Application"
": Application" --> FM: ask data
deactivate ": Application"

FM -> ": Application": inputs required data
activate ": Application"
": Application" --> FM: informs result
deactivate ": Application"
deactivate FM
@enduml
````

## 1.2 Dependency of other User Stories

This User Story has dependencies with the [US150], since it will be necessary to access the user 
profile in order to get the list of his own emails and then choose the secondary email that he 
wishes to delete.

# 2. Analysis

## 2.1 Email Account entry

According to what was described, the email account will be deleted upon request from the Family
Member. The email account should already be created with an alphanumeric string as its email address and
repeated emails should not exist.

The email is an instance of {Email}.

The email has the following attributes:

| Attributes   | Rules                                        |
| ------------ | -------------------------------------------- |
| email | unique, required, alphanumeric (String). <br> A regular expression (regex) will be used to validate the email format. |

## 2.2 Domain Model Excerpt

The relevant extract of the domain model is represented below:

````puml
@startuml
title Domain Model
header DM
hide methods

object Person{
- id
- name
- birthDate
- VAT
- address
}

object EmailAddressList{
-email
}

Person "1" - "0..*" EmailAddressList : has

@enduml
````

# 3. Design

## 3.1. Functionality Realization

The requirements specified in [Analysis](#2-analysis) should be taken into account when removing 
a secondary email account.

The Sequence Diagram (SD) is the following:

```puml
@startuml US152_SD
header SD
autonumber
title US152 Delete a Secondary Email From Family Member Profile
actor "Family Member" as FM
participant ": UI" as UI
participant ": PersonController" as PIC
participant "personService \n: PersonService" as PS
participant "anEmail \n: Email" as email
participant "personRepository \n: PersonRepository" as PR
participant "aPerson \n: Person" as P
participant "EmailListMapper" as ELM

FM -> UI : Add Email
activate FM
activate UI
UI -> PIC : removeEmail(personId, email)
activate PIC
|||
PIC -> PS : removeEmail(personId, email)
activate PS
PS -> email** : create(personId)
PS -> PR : getByEmail(anEmail)
activate PR
PR -> P **: create ()
ref over PR
  getByEmail()
end ref
PR --> PS : aPerson
deactivate PR
PS --> PR : existsByEmailJPA(email)
activate PR
ref over PR
existsByEmailJPA()
end ref
PR --> PS : anEmailJPA
deactivate PR
|||
alt if !existsByEmailJPA(email)
PS --> P : removeEmail(email)
activate P 
ref over P
removeEmail()
end ref
P --> PS : emailAdresses
deactivate P
PS --> PR ++: save (aPerson)
deactivate PR
PS -> ELM ++: toDTO(aPerson)
ref over ELM : toDTO()
return emailListDTO
end
PS --> PIC : emailListDTO
deactivate PS
PIC --> UI: ResponseEntity<EmailListDTO>
deactivate PIC
UI --> FM : Informs Result
deactivate UI
deactivate FM

@enduml
```

```puml
@startuml
header ref
autonumber
title getByEmail()

participant "personRepository \n: PersonRepository" as PR
participant "anEmailJPA\n: EmailJPA" as emailjpa
participant ": iPersonRepositoryJPA" as iPRJPA
participant "personDomainDataAssembler \n: PersonDomainDataassembler" as DA
participant "aPersonJPA \n: PersonJPA" as aPersonJPA
participant "otherPhoneNumbersJPA \n: List<OtherPhoneNumberJPA>" as phones
participant "aPerson\n: Person" as aPerson

-> PR : getByEmail(anEmail)
activate PR
PR --> emailjpa** : create (anEmail.getEmailAddress)
PR -> iPRJPA: getByIdIncludingPhoneNumbers(anEmailJPA)
activate iPRJPA
iPRJPA --> PR : aPersonJPAOptional
deactivate iPRJPA
|||
PR -> aPersonJPA** : personJPAOptional.get()
activate aPersonJPA
aPersonJPA -> phones** : getPhoneNumbers()
aPersonJPA --> PR : otherPhoneNumbersJPA
deactivate aPersonJPA
|||
PR -> DA : phoneNumbersJPAToDomain(otherPhoneNumbersJPA)
activate DA
DA --> PR : phoneNumbers
deactivate DA
|||
PR -> iPRJPA : getOtherEmailsById(anEmailJPA)
activate iPRJPA
iPRJPA --> PR : otherEmailAddressesJPA
deactivate iPRJPA
|||
PR -> DA : emailAddressesJPATODomain(otherEmailAddressesJPA)
activate DA
DA --> PR : emailAddresses
deactivate DA
|||
PR --> DA : JPAtoDomain(aPersonJPA)
activate DA
ref over DA
  JPAtoDomain()
end ref
DA --> PR : familyMemberVOs
deactivate DA
PR --> aPerson** : create(personId, personName, birthdate, personVat, familyId, address, phoneNumbers, emailAddresses)
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
header ref
autonumber
title exitsByEmailJPA()

participant "personRepository \n: PersonRepository" as PR
participant "anEmailJPA\n: EmailJPA" as emailjpa

-> PR: existsByEmailJPA (email)
activate PR
PR --> emailjpa** : create (email.getEmailAddress)
<-- PR : anEmailJPA
deactivate PR

@enduml
```

```puml
@startuml
Header ref
title removeEmail()
autonumber

participant ": aPerson\n:Person" as personS
participant "anEmail\n:Email" as anEmail

->  personS: removeEmail(email)
activate personS
personS --> anEmail** : create(email)
personS -> personS: result= hasEmail(anEmail)

opt result==true

personS -> personS: emailAddresses.remove(anEmail)
end 


[<--  personS : emailAddresses
deactivate personS

@enduml
```

```puml

@startuml
header ref
autonumber
title mapper.toDTO()

participant "emailListMapper \n: EmailListMapper" as ELM

-> ELM: toDTO (aPerson)
activate ELM
ELM ->ELM : getEmailAddresses(aPerson)
ELM --> "emailListDTO:\nEmailListDTO"  ** : create (emailAddresses)
<-- ELM : EmailListDTO
deactivate ELM


@enduml
```


## 3.2. Class Diagram
The Class Diagram is the following:
```puml
@startuml
title Class Diagram
skinparam linetype ortho

class PersonController {
    + removeEmail()
}

interface IAddEmailController{
    removeEmail()
}

class PersonService {
    + removeEmail()
}

interface IPersonService {
   removeEmail()
}

class PersonDomainDataAssembler {
    + JPAToDomain()
    + emailAddressesJPATODomain()
    + phoneNumbersJPAToDomain()
}

class PersonRepository {
    + getByEmail()
    + existsByEmailJPA()
}

interface IPersonRepositoryJPA {
    findById()
    getByIdIncludingPhoneNumbers()
    getOtherEmailsById()
}

interface CrudRepository {
    findById()
}

class FamilyMemberVOs {
        - personName: PersonName
        - personVat: PersonVat
        - birthDate: BirthDate
        - address: Address
}

package "PersonJPA Aggregate" {
    class PersonJPA <<Entity>> {
        - personNameJPA: PersonNameJPA
        - personVatJPA: PersonVatJPA
        - birthDateJPA: BirthDateJPA
        - addressJPA: AddressJPA
        + getId()
        + getName()
        + getBirthDate()
        + getVat()
        + getAddress()
        + getPhoneNumbers()
        + getEmailAddresses()
        + get FamilyId()
    }
}

package "PersonAggregate" {
    class Person <<Entity>> <<Root>> {
        - personName: PersonName
        - personVat: PersonVat
        - birthDate: BirthDate
        - address: Address
        + getId()
        + getName()
        + getBirthDate()
        + getVat()
        + getAddress()
        + getPhoneNumbers()
        + getEmailAddresses()
    }
}

class EmailJPA <<Value Object>> <<Id>>{
    - email: String
}

class FamilyIdJPA <<Value Object>>{
    - familyId: int
}


class PhoneNumberJPA <<Value Object>> {
    - phoneNumber: String
}


class Email <<Value Object>> <<Id>>{
    - email: String
}

class FamilyId <<Value Object>>{
    - familyId: int
}

class PhoneNumber <<Value Object>> {
    - phoneNumber: String
}


PersonController "1" .u.> IAddEmailController
PersonService "1" .l> "1" IPersonService
PersonController -.> IPersonService
PersonService "1" ..> "1" IPersonRepositoryJPA
PersonService "1" ..> "1"PersonDomainDataAssembler
IPersonRepositoryJPA "1" -|> "1" CrudRepository
PersonService "1" ..> "1" Person
PersonService "1" ..> "1" PersonRepository
PersonRepository "1" ..> "1" IPersonRepositoryJPA

Person "1" *-- "1" Email : emailAddress >
Person "1" *-- "0..*" Email : emailAddresses >
Person "1..*" *-- "1" FamilyId : familyId >
Person "1" *-- "0..*" PhoneNumber : phoneNumbers >

PersonJPA "1" *-- "1" EmailJPA : emailAddress 
PersonJPA "1" *-- "0..*" EmailJPA : emailAddresses >
PersonJPA "1..*" *-- "1" FamilyIdJPA : familyId >
PersonJPA "1" *-- "0..*" PhoneNumberJPA : phoneNumbers >

PersonDomainDataAssembler "1" *-- "1" PersonJPA
PersonDomainDataAssembler "1" *-- "1" Person
IPersonRepositoryJPA "1" *-- "0..*" PersonJPA 

FamilyMemberVOs "1" *-- "1" Email : emailAddress >
FamilyMemberVOs "1" *-- "1" FamilyId : familyId >
FamilyMemberVOs "1" *-- "1" Person

@enduml
```

## 3.3. Applied Patterns

The applied patterns were the following:

- *Single Responsibility Principle (SRP)*  - All classes have one responsibility, which means, only
  one reason to change;
- *Controller* - The controller (AddEmailController) receives and coordinates system operations
  connecting the UI layer to the App's logic layer;
- Information Expert - Each class was assigned responsibilities that can be fulfilled because they
  have the information needed to do so;
- *Pure Fabrication* - The PersonRepository class is a class that does not represent a problem
  domain concept, nevertheless it was assigned a set of responsibilities to support high cohesion,
  low coupling and reuse;
- *Creator* - Person class was assigned the responsibility to instantiate an Email, because it had
  the necessary data that would be passed on to it.
- *Low Coupling* - Classes were assigned responsibilities so that coupling remains as low as
  possible, reducing the impact of any changes made to objects later on;
- *High Cohesion* - Classes were assigned responsibilities so that cohesion remains high(they are
  strongly related and highly focused). This helps to keep objects understandable and manageable,
  and also goes hand in hand with the low coupling principle.

## 3.4. Tests

### 3.4.1 Unit Tests

The Unit Tests are defined below:

- **Unit Test 1:** Assert the creation of an email.

 ```java
  @Test
```

- **Unit Test 2:** Verify that email is not added when email is null.

 ```java
```

- **Unit Test 3:** Verify that email is not added when email has invalid format.

 ```java
```

- **Unit Test 4:** Verify that it is not possible to create an instance of the class Email with an
  empty value.

```java
```  

- **Unit Test 5:** Verify that an existing email address in the list of emails is not added.

 ```java
```

# 4. Implementation

The main challenge encountered during the implementation of this functionality was:

- The dependency on other functionalities to implement and test this functionality correctly.

In order to minimize these difficulties and to guarantee a reliable functionality, the tests were
done to cover the maximum scenarios, identifying and correcting the encountered errors.

# 5. Integration/Demonstration

At the moment, there are no User Stories dependent on the development of this User Story, therefore
its integration with other functionalities/features cannot be tested further.

# 6. Observations

[US010]: US010_Create_Family.md

[US011]: US011_Add_Family_Administrator.md

[US101]: US101_Add_Family_Member.md