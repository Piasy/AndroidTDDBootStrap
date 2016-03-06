#!/bin/sh
./gradlew clean :model:check :model:cAT :model:jacocoReport --stacktrace
