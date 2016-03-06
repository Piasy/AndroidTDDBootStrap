#!/bin/sh
./gradlew clean okbuck && ./buck/bin/buck install -r appProductRelease
