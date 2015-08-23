#!/bin/sh
buildsystem/lint.sh && buildsystem/testCommon.sh && buildsystem/testCommonAndroid.sh && buildsystem/testModel.sh && buildsystem/testPresentation.sh
