#!/bin/sh
./gradlew :once:once:clean :once:once:testDebug && buildsystem/checkCommon.sh && buildsystem/checkCommonAndroid.sh && buildsystem/checkModel.sh && buildsystem/checkPresentation.sh
