#!/bin/sh
./gradlew :app:clean :app:check --stacktrace
# emulator will hang when running espresso test, so disable it in ci
# && buildsystem/checkEspresso.sh
