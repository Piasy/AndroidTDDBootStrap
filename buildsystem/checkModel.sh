#!/bin/sh
./gradlew :model:clean :model:check :model:connectedAndroidTest --stacktrace
