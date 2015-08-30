#!/bin/sh
./gradlew :once:once:clean :once:once:testDebug && buildsystem/testCommon.sh && buildsystem/testCommonAndroid.sh && buildsystem/testModel.sh && buildsystem/testPresentation.sh
