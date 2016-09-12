#!/bin/sh
./gradlew clean :base:check :base:jacocoReport :model:check :model:cC :model:jacocoReport :app:check :app:cC :app:jacocoReport --stacktrace
