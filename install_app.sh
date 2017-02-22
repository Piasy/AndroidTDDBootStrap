#!/bin/bash

BUILD_TYPE=$1

if [[ -z "${BUILD_TYPE// }" ]]; then
    BUILD_TYPE="debug"
fi

BUILD_TASK="$(tr '[:lower:]' '[:upper:]' <<< ${BUILD_TYPE:0:1})${BUILD_TYPE:1}"

export ANDROID_TDD_BOOTSTRAP_COMPOSE_BUILD=true
./gradlew :app:assemble${BUILD_TASK} --offline
unset ANDROID_TDD_BOOTSTRAP_COMPOSE_BUILD
adb install -r app/build/outputs/apk/app-${BUILD_TYPE}.apk
adb shell am start -n com.github.piasy.octostars/com.github.piasy.octostars.features.splash.SplashActivity
