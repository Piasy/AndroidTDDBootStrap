#!/bin/sh
./gradlew clean :base:check :base:jacocoReport :model:check :model:jacocoReport :app:check :app:jacocoReport --stacktrace
