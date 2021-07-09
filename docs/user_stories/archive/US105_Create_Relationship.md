US105 Create a Relation Between Two Family Members
=======================================

# 1. Requirements

*As a family administrator, I want to create a relation between two family
members.*

From the project proposal: *"There are family relations in a family (son,
daughter, husband, wife, father, mother, etc.). All relations use the level of
the main user as a reference."*

The functionality will allow a family administrator to create a relationship
between themselves and another member of the same family. It was designed to
respond to the user story/requirement referred above.

The development team designed this functionality to allow the creation these
relationships only between members of the family in relation to the family
administrator, in order to simplify the information provided to the user.

## 1.1 System sequence diagram

```plantuml
@startuml
header ssd
title US105 Create relationship
autonumber
actor "Family Administrator" as FA
actor ": Application" as App

FA -> App : create relationship
activate FA
activate App
App --> FA : ask to choose relationship and member from list
deactivate App
|||
FA -> App : select relationship
activate App
App --> FA : inform result
deactivate App
deactivate FA

@enduml
```

# 1.2. Dependency of other user stories

This user story is dependent on [US010], [US011] and [US101], because without
having a family with an administrator and at least another family member, it
would be impossible to create a relationship.

Directly, this functionality will be necessary for [US104] because without it no
relationships between members would exist to be retrieved.

# 2. Analysis

The analysis of this user story is divided in two main sections:

### 2.1 Relationship creation

The relationship object, that corresponds to the relationship between the
administrator and another member of the same family, as mentioned before, will
have the following attributes:

Attributes       | Rules
-----------------|------------------
relationshipType | required, enum type constant
personId         | unique, required, integer

The possible relationship types implemented in this sprint are:

- Husband/Wife
- Partner
- Parent
- Child
- Sibling
- Grandparent
- Grandchild
- Uncle/Aunt
- Nephew/Niece
- Cousin

Each of these types will be represented by an _enum_ type constant, with a
numeric value attributed to it. Either the constant value and its numeric value
can be used to choose a relationship type. This list can be incremented in the
future, if necessary, with no repercussions.

### 2.2 Relevant domain model excerpt

Below is the excerpt of the domain model that is relevant for this user story.

```puml
@startuml
title Domain Model US105
hide methods

object Family {
  - familyId
  - name
  - registrationDate
  - administrator
  - personList
  - familyRelationshipList
}

Family "1" - "0..*" FamilyRelationship : has

object FamilyRelationship{
 - relationshipType
 - personId
}

object Person {
 - personId
 - name
 - birthDate
  - phone
  - emailList
  - VAT
  - address
}

FamilyRelationship "1" - "1" Person : with Main User

@enduml
```

# 3. Design

## 3.1. Functionality development

In the diagrams below, it is possible to see the implementation strategy for
this user story.

### 3.1.1 Sequence diagrams

```plantuml
@startuml

header sd
title US105 Create relationship
autonumber
actor "Family Administrator" as admin
participant ": UI" as UI
participant ": CreateRelationship\nController" as controller
participant ": Application" as app
participant "familyService:\nFamilyService" as famService

admin -> UI : create relationship
activate admin
activate UI
UI -> controller : getRelationshipTypesList()
activate controller
controller -> app : getFamilyService()
activate app
app --> controller : familyService
deactivate app
controller -> famService : getRelationshipTypesList()
activate famService
famService --> controller : relationshipTypesList
deactivate famService
controller -> UI : relationshipTypeslist
UI -> controller : getFamilyMemberListWithoutAdmin(familyId)

controller -> famService : getFamilyMemberListWithoutAdmin(familyId)
activate famService
ref over famService
  getFamilyMemberListWithoutAdmin(familyId)
end ref
famService --> controller : familyMemberListWithoutAdmin
deactivate famService
controller --> UI : familyMemberListWithoutAdmin
deactivate controller
UI --> admin: ask to choose relationship type and member from list
deactivate UI

admin -> UI: select relationship type and member
activate UI
UI -> controller: createRelationship(familyId, personId, relationshipType)
activate controller

|||
controller -> famService: existsRelationship(familyId, personId)
activate famService
ref over famService
  existsRelationship(familyId, personId)
end ref
alt the relationship does not exist yet
    famService --> controller: false
    deactivate famService
    controller --> famService: createRelationship(familyId, personId, relationshipType)
    activate famService
    ref over famService
        createRelationship(familyId, personId, relationshipType)
    end ref
    famService --> controller: true
    controller --> UI: true
    UI --> admin : inform success
else the relationship already exists
    famService --> controller: false
    deactivate famService
    controller --> UI: inform failure
    deactivate controller
    UI --> admin: inform failure
    deactivate UI
    deactivate admin

end
@enduml
```

```plantuml
@startuml

header ref
title getFamilyMemberListWithoutAdmin()
autonumber
participant "familyService:\nFamilyService" as famService
participant "familyMembersWithoutAdminList:\nList<Person>" as famMembersNoAdmin
participant "aFamily: \n Family" as family
participant "aPerson: \n Person" as person

-> famService : getFamilyMemberListWithoutAdmin(familyId)
activate famService 

famService -> famService: getFamilyById(familyId)


loop for each person in the familyMemberList
    famService -> family: getFamilyMembersList()
    activate family
    family -> person: result = (isAdministrator(person))
    deactivate family
    activate person
    person -> famService: boolean = result
    deactivate person
        opt result == false
          famService -> famMembersNoAdmin**: add(person)
        end
end

<-- famService : familyMembersWithoutAdminList
deactivate famService

@enduml
```

```plantuml
@startuml

header ref
title existsRelationship()
autonumber
participant "familyService \n: FamilyService" as famService
participant "family \n: Family" as fam
participant "familyRelationship \n: FamilyRelationship" as famRel
participant "aPerson: \n Person" as person

-> famService : existsRelationship(familyId, personId)
activate famService 
famService -> famService: getFamilyById(familyId)
famService -> fam: checkIfPersonExistsInFamily(personId)
activate fam

loop for each person in PersonList
fam -> person: getPersonId()
activate person
person -> fam:personId
deactivate person
end
fam -> famService: boolean = true
deactivate fam

loop for each relationship in familyRelationshipList
    famService -> fam: getFamilyRelationshipsList()
    activate fam
    fam -> famRel: checkIfRelationshipRefersToMember(personId)
    activate famRel
    famRel -> fam: boolean
    deactivate famRel
    fam -> famService: boolean 
    deactivate fam
end
<-- famService : boolean
deactivate famService

@enduml
```

```plantuml
@startuml

header ref
title createRelationship()
autonumber
participant "familyService \n: FamilyService" as famService
participant "family \n: Family" as fam
participant "relationship: \n FamilyRelationship" as familyRelationship

--> famService : createRelationship(familyId, personId, relationshipType)
activate famService 
famService -> famService: getFamilyById(familyId)
famService --> familyRelationship **: createRelationship(relationshipType, personId)
famService -> fam : addRelationship(relationship) 
activate fam
deactivate fam

<-- famService : boolean
deactivate famService

@enduml
```

## 3.2. Class Diagram

In the diagram below, all classes involved in this US are represented, as well
as their interactions.

```plantuml
@startuml
title Class Diagram US105
class CreateRelationshipController{
    - application : Application
    getFamilyService()
    getRelationshipTypesList()
    getFamilyMembersListWithoutAdmin(familyId)
    existsRelationship(familyId, personId)
    createRelationship(familyId, personId, relationshipType)
}

CreateRelationshipController "1" - "1" Application

class Application{
    - categoryService : CategoryService
    - familyService : FamilyService
    - personService : PersonService
    - accountService: AccountService
    getCategoryService()
    getFamilyService()
    getPersonService()
    getCashAccountService()
}

Application "1" - "1" FamilyService : calls


class FamilyService{
    - familyList: List<Family>
    getFamilyMembersListWithoutAdmin(familyId)
    getFamilyById(familyId)
    existsRelationship(member)
    createRelationship(member, idRelationship)
    addRelationship(relationship)
    getFamilyRelationshipsList()
}

FamilyService "1" -- "1" Family : calls >

class Family{
    - name: String
    - familyId: int
    - registrationDate: Date
    - administrator: Person
    - personList<Person>
    - familyRelationships: List<FamilyRelationship>
    getFamilyId()
    isAdministrator()
    getFamilyRelationshipsList()
    addFamilyRelationship(relationship)
}

class Person{
- personId
    getPersonId()
}
Family "1" -- "0*" Person : calls >


Family "1" -l- "1" FamilyRelationship : calls >

class FamilyRelationship{
    - relationshipType : FamilyRelationshipType
    - personId: int
    createRelationship(personId, relationshipType)
    checkIfRelationshipRefersToMember(personId)
}

FamilyRelationship "1" -l- "1" FamilyRelationshipType : calls >

Enum FamilyRelationshipType{
    - numericValue
    valueOf(numericValue)
}

@enduml
```

## 3.3. Applied patterns

- *Single Responsibility Principle (SRP)* - FamilyRelationship and
  FamilyRelationshipType classes follow this principle, as they have one
  responsibility, which is to manage the information included within them.
- *Controller* - CreateRelationshipController receives and coordinates system
  operations, as it connects the UI layer to the application logic layer.
- *Information Expert* - To each class were assigned responsibilities that can
  be fulfilled because they have the information needed to do so;
- *Creator* - FamilyService class was assigned the responsibility to instantiate
  a FamilyRelationship, because it had the necessary data that would be passed
  on to it.
- *Pure Fabrication* - FamilyService class is a class that does not represent a
  domain concept, and it was assigned a set of responsibilities to support high
  cohesion, low coupling and reuse.
- *Low Coupling* - Classes were assigned responsibilities so that coupling
  remains as low as possible, reducing the impact of any changes made to objects
  later on;
- *High Cohesion* - Classes were assigned responsibilities so that cohesion
  remains high (they are strongly related and highly focused). This helps to
  keep objects understandable and manageable, and also goes hand in hand with
  the low coupling principle.

## 3.4. Domain tests

### 3.4.1. Integration testing

Below is the list of integration tests, organized by the correspondent class:

- **Controller**
    - **Integration test 1:** Verify that it is possible to get the list of
      relationship types successfully;
    - **Integration test 2:** Verify that it is possible to get the list of
      members, except the administrator, of a family successfully (two cases);
    - **Integration test 3:** Verify that it is not possible to get the list of
      members, except the administrator, of a family when the family Id does not
      exist;
    - **Integration test 4:** Verify that it is possible to create a
      relationship successfully;
    - **Integration test 5:** Verify that it is not possible to create a
      relationship with a family id that does not exist;
    - **Integration test 6:** Verify that it is not possible to create a
      relationship with a person id that does not exist;
    - **Integration test 7:** verify that it is not possible to create a
      relationship when the family member already has one with the
      administrator;

- **FamilyService**
    - **Integration test 1:** Verify that it is possible to get the list of
      relationship types successfully (three cases);
    - **Integration test 2:** Verify that it is possible to get the list of
      members, except the administrator, of a family successfully (three cases);
    - **Integration test 3:** Verify that it is possible to create a
      relationship successfully (two cases);
    - **Integration test 4:** Verify that it is not possible to create a
      relationship with a family id that does not exist;
    - **Integration test 5:** Verify that it is not possible to create a
      relationship with a person id that does not exist;
    - **Integration test 6:** Verify that it is possible to check that a
      relationship between the family member and the administrator already
      exists;
    - **Integration test 7:** Verify that it is possible to check that a
      relationship between the family member and the administrator doesn't exist
      yet;

- **Family**
    - **Integration test 1:** Verify that a person exists in a family
      successfully (two cases);
    - **Integration test 2:** Verify that a person does not exist in a family
      successfully;
    - **Integration test 3:** Verify that it is possible to get the family
      administrator successfully (two cases);
    - **Integration test 4:** Verify that, when there is no administrator, it is
      not possible to get the family administrator;
    - **Integration test 5:** Verify that it is possible to get the list of
      family relationships successfully, even when there are no relationships
      yet (three cases);
    - **Integration test 6:** Verify that it is possible to add a new
      relationship to the family relationships list successfully;

### 3.4.1. Unit testing

Below is the list of integration tests, organized by the correspondent class:

- **FamilyRelationship**
    - **Unit test 1:** Verify that it is possible to create a new relationship
      with the valid relationship type and person Id;
    - **Unit test 2:** Verify that it is not possible to create a new
      relationship with an invalid person Id;
    - **Unit test 3:** Verify that it is possible to create a new relationship
      with a valid relationship numeric value and person Id;
    - **Unit test 4:** Verify that it is not possible to create a new
      relationship with an invalid relationship numeric value (two cases);

- **FamilyRelationshipType**
    - **Unit test 1:** Verify that it is possible to return a family
      relationship type with the corresponding numeric value as input (two
      cases)
    - **Unit test 2:** Verify that it is not possible to return a family
      relationship type when the numeric value input doesn't correspond to any
      relationship type (two cases)

# 4. Implementation

The main challenges that were found while implementing this functionality were:

- The need for a strategy to store the relationship types that would allow the
  tester to choose from a numeric value if necessary;
- The dependency of other functionalities to be ready, to be able to test the
  implementation properly;
- The novelty of having to interpret a user story that may contain a few
  ambiguities;

To minimize these difficulties, a lot of research and study of reliable
documentation was done. There was communication with the Product Owner whenever
needed, to clarify some requirements.

So that we could present a reliable functionality, many tests were done, to
identify as many possible errors in the implementation as possible.

# 5. Integration/Demonstration

As mentioned before, this functionality will be indirectly necessary for
[US104] (*"As a family administrator, I want to get the list of family members
and their relations"*). The development of [US104] helps to prove a good
integration with this functionality and its system components.

At the moment, no other user stories are dependent on this one, so its
integration with other functionalities cannot be tested further.

# 6. Observations

As mentioned before, at the moment, only relationships between the administrator
and another family member are possible. For future sprints, broadening this
functionality so that relationships could be created between any two family
members is a possibility, if agreed or seen as necessary by the Product Owner.

# 7. Changelog

02/02/2021, Jaqueline Andre:
The team saw the need to change the implementation of this user story, so as to allow the 
creation of a relationship between any two members of the family and not just with the administrator and another 
family member. This facilitates the implementation of [US188].

[US010]: US010_Create_Family.md
[US011]: US011_Add_Family_Administrator.md
[US101]: US101_Add_Family_Member.md
[US104]: US104_Get_List_Of_Members_And_Relations.md
[US188]: [US188_Check_Balance_Of_A_Family_s_Children.md]