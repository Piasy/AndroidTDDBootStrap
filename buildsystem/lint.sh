#!/bin/sh

./gradlew lint 2> /dev/null | grep -E "[1-9]\d? issues found"
if [ $? == 0 ]; then
    exit 1
else
    exit 0
fi
