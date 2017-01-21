#!/bin/sh
adb shell setprop dalvik.vm.dexopt-flags v=n,o=v && \
./gradlew \
:contrib:piasy:users:cC \
:app:cC --stacktrace
