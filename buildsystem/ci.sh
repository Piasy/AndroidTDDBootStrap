#!/bin/sh
./gradlew --stacktrace :base:check :base:jacocoReport \
:app:check :app:jacocoReport \
:contrib:piasy:misc:check :contrib:piasy:misc:jacocoReport \
:contrib:piasy:users:check :contrib:piasy:users:jacocoReport \
:contrib:piasy:repos:check :contrib:piasy:repos:jacocoReport
