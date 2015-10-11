#!/bin/sh
adb shell setprop dalvik.vm.dexopt-flags v=n,o=v && adb shell stop installd && adb shell start installd && ./gradlew :presentation:connectedAndroidTest --stacktrace
