#!/bin/sh
./gradlew clean :base:check :base:jacocoReport \
:contrib:piasy:misc:check :contrib:piasy:misc:jacocoReport \
:contrib:piasy:users:check :contrib:piasy:users:jacocoReport \
:app:check :app:jacocoReport --stacktrace
