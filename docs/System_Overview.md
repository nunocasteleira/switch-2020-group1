# System Overview

For now, our approach is the following:

````puml
@startuml
interface UI[
UI
]
component FFMS <<component>>  as "Family Finance Management System" {
}
UI -# FFMS

@enduml
````

## Logic View (Level Two)

````puml
@startuml
interface UI as "UI"

component FFMS <<component>>  as " Family Finance Management System" {
component FFM_UI <<component>> as "FFM_UI"
interface BL as "BL_API"
component FFM_BL <<component>> as "FFM_BusinessLogic"
}

UI -# FFMS

FFMS - FFM_UI

FFM_UI #-( BL
BL )- FFM_BL

note bottom of FFM_UI: Not yet\nbeing developed.

@enduml
````

## Business Logic (Level Three)

````puml
@startuml
interface BL_API as "BL_API"

component FFM_BL <<component>>  as " FFM_BusinessLogic" {
component Ctrl <<component>> as "Controllers"
interface M_API as "Model_API"
interface S_API as "Service_API"
component FFM_M <<component>> as "Model"
component FFM_S <<component>> as "Services"
}

BL_API -# FFM_BL

FFM_BL - Ctrl
Ctrl -( M_API
M_API - FFM_M
M_API )- FFM_S
Ctrl --( S_API
S_API )- FFM_S

FFM_M --[hidden] FFM_S
FFM_M --[hidden] FFM_S
FFM_M --[hidden] FFM_S
@enduml
````