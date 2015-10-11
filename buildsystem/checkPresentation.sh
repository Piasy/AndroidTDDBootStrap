#!/bin/sh
./gradlew :presentation:clean :presentation:check --stacktrace
# emulator will hang when running espresso test, so disable it in ci
# && buildsystem/checkEspresso.sh
