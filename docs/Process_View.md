# Process View

## Level Two
For now, our approach is the following:

```plantuml
@startuml

autonumber
actor "Any System Actor" as sysAct
participant ": Application" as app
participant ": App_BusinessLogic" as logic

sysAct -> app : request some data
activate sysAct
activate app
app -> logic : getSomeData(...)
activate logic
logic -> app : data
deactivate logic
app --> sysAct : shows data
deactivate app
|||
sysAct -> app : request do add some data
activate app
app -> logic : addData(...)
activate logic
logic -> app : result
deactivate logic
app --> sysAct : shows result
deactivate app
deactivate sysAct

@enduml
```

##Level Three

```plantuml
@startuml

autonumber
actor "Any System Actor" as actor
participant ": FFM_UI" as UI
participant ": (x11)Controller" as controller
participant ": (x1)Application" as app
participant ": (x4)Service" as service
participant ": (x10)Model" as model
participant "objx10 \n: (x10)Model" as objModel

actor -> UI : request some data
activate actor
activate UI
UI -> controller : getSomeData()
activate controller
controller -> app : getService(...)
activate app
app -> controller : service
deactivate app
controller -> service : getSomeData(...)
activate service
service -> controller : data
deactivate service
controller -> UI : data
deactivate controller
UI -> actor : shows data
deactivate UI
deactivate service

actor -> UI : request to add some data
activate UI
UI -> controller : addData(...)
activate controller
controller -> service : addSomething(...)
activate service
service -> objModel **: create (...)
service -> service : add(objx10)
service -> controller : result
deactivate service
controller -> UI : result
deactivate controller
UI -> actor : shows result
deactivate UI
deactivate actor

@end
```
