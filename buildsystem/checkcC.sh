#!/bin/sh
adb shell setprop dalvik.vm.dexopt-flags v=n,o=v && \
./gradlew --stacktrace :app:cC \
:contrib:piasy:users:cC
