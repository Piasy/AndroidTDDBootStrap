#!/bin/sh
./gradlew clean :model:check :model:cC :model:jacocoReport --stacktrace
