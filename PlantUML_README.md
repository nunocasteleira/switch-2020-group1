# PlantUML Script

## Context

To generate documentation diagrams you simply enclose your diagrams in HTML comments inside the
documentation `.md` files.

HTML comments are written such as:

```txt
<---
    HTML are written inside these arrows.
    For Markdown comments triple dash in the opening diagraph is recomended, but not mandatory.
-->
```

You can name your outputted files using the following:

```txt
<---
@startuml US111_SD
    ''uml code here
@enduml
-->
```

Be careful in class and sequence diagrams of the closing diagraph, as it can be confused with the
dashed arrow used for creating objects (sequence diagrams) and used to "uses" or "implements"
cases (class diagram). No solution was found for the first problem, but in class diagrams you can
break the diagraph using `-u->` (or any directional annotation as `d`, `u`, `l`, or `r`). You can put
as many dashes as desired before the letter (`---u-|>` for example).

## Run the Script

To run the build script, at the root of this project, you simply issue

```bash
$ ./build_plantuml.sh
```

This will generate ALL diagrams found at the documentation to the folder
`docs/user_stories/diagrams`. Internally, the script is using a flag to check if the metadata
coincides with the code block, so untouched diagrams will not be re-generated.

You can be specific of which User Story's diagrams are to be generated, passing it as the only 
argument of the script:

```bash
$ ./build_plantuml.sh US111
```

This will search the beginning of the `.md` files to match the passed string.

## How to include the diagrams in your documents

Following Markdown's syntax, you include a image using the following:

```txt
![Alt Text](path/to/file.png)

As an example:
![US104 SSD](diagrams/US104_SSD.png)
```

I prefer to include this tag just below the diagram code, for readability:

```txt
<!--
@startuml US104_SSD
autonumber
header SSD
title Get list of family members and their relations
actor "Family Administrator" as FM
participant ": Application" as App

FM -> App ++: Get list of members and their relations
activate FM

return Show list of members and their relations
deactivate FM
@enduml
-->

![US104 SSD](diagrams/US104_SSD.png)
```