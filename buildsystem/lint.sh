#!/bin/sh
# won't work properly after import Kotlin, not force it in ci
./gradlew lint 2> /dev/null | grep -E "[1-9]\d? issues found"
if [ $? == 0 ]; then
    exit 1
else
    exit 0
fi
