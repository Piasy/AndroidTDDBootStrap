#!/bin/sh

MOD_PATH=$1

mkdir -p ${MOD_PATH}

MODULES=`cat settings.gradle`
NEW_MODULE=":${MOD_PATH//\//:}"
echo "$MODULES,\n\t\t'$NEW_MODULE'" > settings.gradle

CI_COMMANDS="`cat buildsystem/ci.sh` \\"
echo "$CI_COMMANDS" > buildsystem/ci.sh
echo "$NEW_MODULE:check $NEW_MODULE:jacocoReport" >> buildsystem/ci.sh

cp buildsystem/contrib_module_template.gradle ${MOD_PATH}/build.gradle
echo "/build/" > ${MOD_PATH}/.gitignore

mkdir -p ${MOD_PATH}/src/main/java
mkdir -p ${MOD_PATH}/src/debug/java

cp buildsystem/contrib_module_template_manifest.xml ${MOD_PATH}/src/main/AndroidManifest.xml
