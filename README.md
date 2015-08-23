#Android template project
[![Master branch build status](https://travis-ci.org/Piasy/AndroidTDDBootStrap.svg?branch=master)](https://travis-ci.org/Piasy/AndroidTDDBootStrap)
[![codecov.io](http://codecov.io/github/Piasy/AndroidTDDBootStrap/coverage.svg?branch=master)](http://codecov.io/github/Piasy/AndroidTDDBootStrap?branch=master)

A collection of new technology, best practice, best architecture, useful tools...

##Build tips
+  sign key config
place KeyStore file in some place, and create a TemplateKeyStore.properties, and config the KeyStore in it, include `keystore`, `keystore.password`, `key.password`, `key.alias`
+  use Android Lint Summary tool: `./gradlew lint`

##Tools
+  [Android Lint Summary](https://github.com/passy/android-lint-summary), View your Android lint issues with style.
+  [XLog](https://github.com/promeG/XLog), Method call logging based on dexposed.

##Dev tips
+  Create utils
  +  Create util class in common/common-android module
  +  Add @Provides annotated provider method in corresponding Module class(UtilsModule.java/AndroidUtilsModule.java)
  +  Add expose method in AppComponent.java
+  Create Activity
+  Unit test
  +  ./gradlew :module:unMock :module:test #if use unmock plugin, the unmock plugin seems have a problem that the gradle task will not run automatically.

##Coverage
![codecov.io](http://codecov.io/github/Piasy/AndroidTDDBootStrap/branch.svg?branch=master)
