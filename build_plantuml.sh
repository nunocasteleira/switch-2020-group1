#!/bin/sh

DOC_PATH="docs/user_stories"
PLANTUML_URL=https://search.maven.org/remotecontent?filepath=net/sourceforge/plantuml/plantuml/1.2021.4/plantuml-1.2021.4.jar

if [ ! -e plantuml.jar ]; then
  curl -kLSs ${PLANTUML_URL} -o plantuml.jar
fi

java -jar plantuml.jar -config "./.plantumlrc" --checkmetadata --progress -o "diagrams" -x "**/archive/**" "${DOC_PATH}/${1:-*}*.md"
