#!/bin/sh
adb shell setprop dalvik.vm.dexopt-flags v=n,o=v && ./gradlew :model:cC :app:cC --stacktrace
