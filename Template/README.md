#Android template project
A collection of new technology, best practice, best architecture, useful tools...

##Build tips
+  sign key config
place KeyStore file in some place, and create a TemplateKeyStore.properties, and config the KeyStore in it, include `keystore`, `keystore.password`, `key.password`, `key.alias`
+  build errors

##Tools
+  [Android Lint Summary](https://github.com/passy/android-lint-summary), View your Android lint issues with style.
+  [XLog](https://github.com/promeG/XLog), Method call logging based on dexposed.

##Dev tips
+  Create utils
  +  Create util class in common/common-android module
  +  Add @Provides annotated provider method in corresponding Module class(UtilsModule.java/AndroidUtilsModule.java)
  +  Add expose method in AppComponent.java
+  Create Activity
