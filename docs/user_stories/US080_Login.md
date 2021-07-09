US080 As a System User, I want to login into the application in order to use it.
=======================================

# 1. Requirements

*US080 As a System User, I want to login into the application in order to use it.*

Below is the System Sequence Diagram (SSD) for this user story:

```puml
@startuml
autonumber
header SSD
title Login into the application

actor "System User" as SU
participant ": WebApplication" as App

SU -> App : Goes to the webapp's page
activate SU
activate App
App --> SU : Asks for credentials (email and password)
deactivate App
SU -> App : Inputs credentials
activate App
return Provides access to webapp
deactivate SU
@enduml
```

# 2. Analysis

For now, what is intended for this sprint is to create an authentication mechanism for the web application and not yet an authorization system.

The development team's goal is only to create a login system for the application user, without there having a registration/signup system in place yet.

When the user tries to access the web page, he will come across the login screen, that will ask for their credentials (email and password). Once the user inputs the credentials, he will have access to all of the application.
There won't be any roles yet, all the system users (system manager, family administrator and family member) will have the same access to all of the app's functionalities for the moment.

## 2.1 Domain Model Excerpt

For quick reference, there's a relevant extract of the domain model.

```plantuml

@startuml
title Domain model US080
header DM
skinparam linetype ortho

object Person {
    - Email
    - Password
    - FamilyId
}

note left
Person will access all 
the apps functionalities 
through the email and 
password
end note

object Roles {
    - name
}

Person "0..*" -- "1..*" Roles : has >

@enduml
```

# 3. Design

## 3.1. Functionality Development

The Sequence Diagram for this user story:

```puml
@startuml
autonumber
header SD
title Log in into the application

actor "System User" as SU
participant ": UI" as UI
participant ": AuthController" as AC
participant "loginRequest\n: loginRequestDTO" as LRDTO
participant "authenticationToken \n: UserNamePassword\nAuthenticationToken" as UNPAT
participant ": Autentication\nManager" as AM
participant ": Security\nContextHolder" as SCH
participant ": JwtUtils" as JU
participant "responseDTO: \nJwtResponseDTO" as JRDTO

activate SU
SU -> UI : Goes to the webapp's page
activate UI
UI --> SU : ask username and password
deactivate UI
SU -> UI : input credentials
activate UI
UI -> AC : authenticateUser(loginRequest)
activate AC
AC -> LRDTO : getEmail()
activate LRDTO
LRDTO --> AC : email
deactivate LRDTO
AC -> LRDTO : getPassword()
activate LRDTO
LRDTO --> AC : password
deactivate LRDTO
AC -> UNPAT** : create(email, password) 
AC -> AM : authenticate(authenticationToken)
activate AM
AM -> A as "authentication : \nAuthentication"** : create()
AM --> AC : authentication
deactivate AM
AC -> SCH : setAuthentication(authentication)
activate SCH 
SCH -> SCH : getContext()
deactivate SCH
AC -> JU : generateJwtToken(authentication)
activate JU
JU --> AC : jwt
deactivate JU
AC -> A : getPrinciple()
activate A
A -> UDI as "userDetails : \nUserDetailsImpl"** : create()
A --> AC : userDetails
deactivate A
AC -> UDI : getAuthorities()
activate UDI
UDI -> UDI : stream()
UDI -> UDI : map(item -> item.getAuthority())
UDI -> UDI : collect(Collectors.toList())
UDI --> AC : role
deactivate UDI
AC -> JRDTO** : create(jwt, userDetails.getUsername(), userDetails.getFamilyId(), role))
AC --> UI : responseEntity(responseDTO)
deactivate AC
UI --> SU : Provides access
deactivate UI
deactivate SU
@enduml
```

## 3.2. Class Diagram

![US080 Class Diagram](diagrams/US080_ClassDiagram.png)

## 3.3. Applied Patterns

In order to achieve best practices in software development, to implement this US
we're using the following:

- *Single Responsibility Principle* - Classes should have one responsibility,
  which means, only one reason to change;
- *Information Expert* - Assign a responsibility to the class that has the
  information needed to fulfill it;
- *Pure Fabrication* - UserDetailsService was implemented to manage all things
  related to the user details.
- *Creator* - UserDetails are created by UserDetailsService;
- *Controller* - AuthController was created;
- *Low Coupling* - Classes were assigned responsibilities so that coupling
  remains as low as possible, reducing the impact of any changes made to objects
  later on. The implementation of interfaces for the controller, service and repository 
  classes reduced the dependency level between them.
- *High Cohesion* - Classes were assigned responsibilities so that cohesion
  remains as high as possible, to keep objects understandable and manageable.
  They are strongly related and highly focused.

## 3.4. Tests

### 3.4.1 Unit Tests

**Test 1:** 

```java

```

**Test 2:**

```java

```

**Test 3:** 

```java

```

### 3.4.2 Integration Tests

**Test 1:**

```java

```

**Test 2:**

```java

```

**Test 3:**

```java

```


# 4. Implementation

The main challenges found while implementing this functionality were the novelty of implementing a login system and lack of knowledge about the JWT authentication system.
 
To minimize this difficulty, a lot of research and study of reliable documentation was done.
 
In order to have a reliable functionality, many tests were done, 
to identify as many possible errors in the
implementation as possible.

# 5. Integration/Demonstration

This US is not directly dependent on any other that we've had so far.

At the moment, all other frontend USs are dependent on this one, since to access other functionalities we now need to login to the web app with the right credentials. 
This means that the integration of this functionality with the others already developed can be tested successfully.

# 6. Observations

The functionality implemented for this user story is very simple and a few improvements will need to be made for it to be more functional.
Adding a registration system to the app will be crucial, as well as adding an authorization mechanism and adding different roles, for different types of users to have access to specific app functionalities.