# US110 Get Family Category Tree

# 1. Requirements

_As a family administrator, I want to get the list of the categories on the
family’s category tree._

Following the required fields given from the proposition, we need to know the
administrator's family identification number.

## 1.1 System Sequence Diagram

This is the SSD representing the interactions between the user and the system.

```puml
@startuml
autonumber
header SSD
title List Family's Category Tree
actor "Family Administrator" as FA
participant ": Application" as App

FA -> App : Get the list of family category tree
activate FA
activate App
App --> FA : Return required list
deactivate App
deactivate FA
@enduml
```

# 2. Analysis

## 2.1 Family Category Tree entry

According to what was presented, a family category tree is created upon request
from the Family Administrator using its family identification number as a
reference this actor's family.

The family category tree is the resultant merge of the standard category list
and the family category list.

The categories are stored in a list where each category has multiple attributes
as stated in the Create Standard Category
document ([US001](US001_Create_Standard_Category.md)).

Each category in the family category tree should have the following attributes:

| **_Attributes_**    | **_Rules_**       |
| :--------------------------------- | :----------------- |
| **categoryId**      | Unique, required, integer, auto-incrementing |
| **name**        | Required, integer |
| **parentId**    | integer |
| **isStandard**    | boolean |

The default isStandard value is false.

## 2.2 Domain Model Excerpt

For quick reference, there's a relevant extract of the domain model.

```plantuml
@startuml

object Family {
    - familyId
    - name
    - registrationDate
    - administrator
    - personList
    - familyRelationships
    - vatList
    - familyCategoryList
}

Category "0..*" - "0..*" Family : hasFamilyCategories

object Category {
    - categoryId
    - designation
    - parentId
    - isStandard
}

@enduml
```

# 3. Design

## 3.1. Functionality Development

The Sequence Diagram is the following:

```puml
@startuml
autonumber
header SD
title Get Family Category Tree
actor "Family Administrator" as FA
participant ": UI" as UI
participant ": GetFamilyCategoryTree\nController" as GFCTC
participant ": Application" as App
participant ": Category\nService" as CS


FA -> UI : Get the list of the categories on the family’s category tree
activate FA
activate UI

UI -> GFCTC : getCategoryTreeDTO(familyId)
activate GFCTC

GFCTC -> App : getCategoryService()
activate App
return categoryService
deactivate App

GFCTC -> CS : getCategoryTreeDTO(familyId)
activate CS

ref over CS
getCategoryTreeDTO()
end ref

CS --> GFCTC  : categoriesTree
deactivate CS

GFCTC --> UI : categoriesTree
deactivate GFCTC

UI --> FA : Show family category list
deactivate UI

deactivate FA
@enduml
```

```puml
autonumber
header ref
title getCategoryTreeDTO()
participant ": Category\nService" as CS

participant ": Family\nService" as FS
participant ":Family" as F
participant ": CategoryTreeMapper" as Mapper

[-> CS : getCategoryTreeDTO(familyId)
activate CS

CS -> FS: getFamilyById(familyId)
activate FS
FS -> F: getFamilyCategoryList()
Activate F
F --> FS: familyCategoryList
Deactivate F
FS --> CS: familyCategoryList
deactivate FS

CS -> CS: getStandardCategoryList()

CS-->Mapper** : create (standardCategoryList,familyCategoryList)

activate Mapper

ref over Mapper 
getCategoryTreeDTO()
end ref

Mapper-->CS: categoriesTree
deactivate Mapper

[<-- CS: categoriesTree

deactivate CS
```

```puml
autonumber
title getCategoryTreeDTO()
participant ": CategoryTreeMapper" as Mapper
participant ": CategoryTreeDTO" as DTO

[-> Mapper : getCategoryTreeDTO()
activate Mapper

Mapper -> Mapper: getCategoriesListsOrdered()

Mapper --> DTO** : create(categoryTreeListOrdered)
activate DTO
DTO -> DTO: getCategoriesTreeToString()
DTO -->Mapper: categoriesTree

deactivate DTO


[<-- Mapper: categoriesTree
deactivate Mapper

```

## 3.2. Class Diagram

```puml
@startuml
title Class Diagram US110

class GetFamilyCategoryTreeController{
    +getCategoryTreeDTO(familyId)
    +getCategoryTreeToString(familyId)
}

GetFamilyCategoryTreeController - Application

class Application{
    +getCategoryService()
    +getFamilyService()
}

Application - FamilyService: calls

class FamilyService {
    - familyList
    + getFamilyById(familyId)
}

Application  -- CategoryService: calls

class CategoryService {
    - categoryList
    - familyService
    + getCategoryTreeDTO(familyId)
    +getStandardCategoryList()
    +getFamilyCategoryList(familyId)
}

class Category{
 - categoryId
 - name
 - parentId
 - isStandard
}

CategoryService *- Category: contains

FamilyService -- CategoryService: knows

FamilyService - Family
Family *-- Category: contains

class Family {
    - familyCategoryList
    + getFamilyCategoryList()
}

class CategoryTreeMapper{
    - standardCategoryList
    - familyCategoryList
    +getCategoryTreeDTO()
    +getCategoriesListsOrdered()
    -mergeCategoriesLists()
    -appendChildCategories()
}
CategoryService -- CategoryTreeMapper

class CategoryTreeDTO{
    - categoriesTree
    + getCategoryTreeList()
    + getCategoriesTreeToString()
    }
    
CategoryTreeMapper -- CategoryTreeDTO

```

## 3.3. Applied Patterns

In order to achieve best practices in software development, to implement this US
we're using the following:

- _Single Responsibility Principle_ - Classes should have one responsibility;
- _Information Expert_ - Assign a responsibility to the class that has the
  information needed to fulfill it;
- _Pure Fabrication_ - Category Service was implemented to manage everything
  related to categories.
- _Controller_ - ListFamilyCategoryTree was created;
- _Low Coupling_ - ListFamilyCategoryTreeService is the only class responsible
  to return the list of family's category tree.
- _High Cohesion_ - ListFamilyCategoryTreeService is the only class responsible
  to return the list of family's category tree.

## 3.4. Tests

**Note:** Every test requires a family to be created in order to store in the
familyCategoryList and also standard categories previously created to build the
tree.

**Test 1:** Obtain the Family Category List Tree as a DTO:

```java
 @Test
    void getCategoryTreeDTO_AsExpected() {
            createMockDataToCreateFamilyCategoryTesting();
            List<Category> expectedList = createMockDataToCreateExpectedCategoryTreeTesting();
        
            CategoryTreeDTO expectedDTO = new CategoryTreeDTO(expectedList);

        CategoryTreeDTO resultDTO = getFamilyCategoryTreeController.getCategoryTreeDTO(familyId);

        assertEquals(expectedDTO.getCategoryTreeList(), resultDTO.getCategoryTreeList());
        }
```

**Test 2:** Obtain the Family Category List Tree as a String:

```java
 @Test
    void getCategoryTreeToString_AsExpected() {
            createMockDataToCreateFamilyCategoryTesting();
            
            String expected = createMockDataToCreateExpectedCategoryTreeToStringTesting();

            String result = getFamilyCategoryTreeController.getCategoryTreeToString(familyId);

            assertEquals(expected, result);
            }
```

**Test 3:** Get an empty category tree list:

```java
    @Test
    void getCategoryTreeDTO_NullList() {
            String familyName = "Silva Costa";
            CreateFamilyController familyController = new CreateFamilyController(application);
            Family family = familyController.createFamily(familyName);
            familyId = family.getFamilyId();

            
            List<Category> expectedList = new ArrayList<>();
        CategoryTreeDTO expectedDTO = new CategoryTreeDTO(expectedList);

        CategoryTreeDTO resultDTO = getFamilyCategoryTreeController.getCategoryTreeDTO(familyId);

        assertEquals(expectedDTO.getCategoryTreeList(), resultDTO.getCategoryTreeList());
        }
```

**Test 4:** Throw exception when family doesn't exist:

```java
@Test
    void getCategoryTreeDTO_FamilyNull() {
            createMockDataToCreateFamilyCategoryTesting();
            UUID familyId = UUID.randomUUID();

            assertThrows(IllegalArgumentException.class, () -> getFamilyCategoryTreeController.getCategoryTreeDTO(familyId));
        }
```

# 4. Implementation

The major challenge on this user story was merging the two  different category 
lists and getting the category's hierarchy right.


# 5. Integration/Demonstration

This user story is dependent on the user story Create Standard
Category ([US001](US001_Create_Standard_Category.md)) in order to be able to
address the CategoryController.

At the moment, no other user stories are dependent on this one, so its
integration with other functionalities cannot be tested further.

# 6. Observations
