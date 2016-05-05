#!/bin/sh
./gradlew okbuck && ./buck/bin/buck install -r appProdRelease
