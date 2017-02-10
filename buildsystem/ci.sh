#!/bin/sh
./gradlew --stacktrace :base:check :base:jacocoReport \
:app:check :app:jacocoReport \
:contrib:business:check :contrib:business:jacocoReport \
:contrib:bridge:check :contrib:bridge:jacocoReport \
:contrib:piasy:users:check :contrib:piasy:users:jacocoReport \
:contrib:piasy:repos:check :contrib:piasy:repos:jacocoReport \
:contrib:piasy:trending:check :contrib:piasy:trending:jacocoReport \
:contrib:piasy:splash:check :contrib:piasy:splash:jacocoReport
