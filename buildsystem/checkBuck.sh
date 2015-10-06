#!/bin/sh
./gradlew clean jar okbuck && buck install presentation
