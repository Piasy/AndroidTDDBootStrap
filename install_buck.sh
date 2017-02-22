#!/bin/bash

MODULE=$1

if [[ -z "${MODULE// }" ]]; then
    echo "Please specify the module name!"
    exit 1
fi

echo "Building $MODULE..."

if [[ "$MODULE" == "app" ]]; then
    export ANDROID_TDD_BOOTSTRAP_COMPOSE_BUILD=true
    ./gradlew okbuck --offline
    BUCK_TARGET="appDebug"
    ./buckw install -r -x ${BUCK_TARGET}
    unset ANDROID_TDD_BOOTSTRAP_COMPOSE_BUILD
    ./gradlew okbuck --offline
else
    BUCK_TARGET="${MODULE//\//-}Debug"
    ./buckw install -r -x ${BUCK_TARGET}
fi
