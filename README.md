# Android TDD bootstrap project
[![Master branch build status](https://travis-ci.org/Piasy/AndroidTDDBootStrap.svg?branch=master)](https://travis-ci.org/Piasy/AndroidTDDBootStrap)
[![codecov.io](http://codecov.io/github/Piasy/AndroidTDDBootStrap/coverage.svg?branch=master)](http://codecov.io/github/Piasy/AndroidTDDBootStrap?branch=master) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidTDDBootStrap-green.svg?style=flat)](https://android-arsenal.com/details/3/2586)

An Android TDD bootstrap project, using a collection of new technology, obeying the best practices, inspired by some popular architectures, and developed with many handy tools.

## Build tips
+  Sign key config
  +  Place KeyStore file in some place, and create a TemplateKeyStore.properties, and config the KeyStore in it, include `keystore`, `keystore.password`, `key.password`, `key.alias`.
+  To clone all submodules, please use `git clone --recursive git@github.com:Piasy/AndroidTDDBootStrap.git`
+  use flavor to control server configuration, use build type to control log behavior
  +  `dev` for development server, `prod` for production server
  +  `debug` enable log and dev tools, disable crash and analytics, `release` against it

## Why another bootstrap project?
From the beginning of the year 2015, our team started a new project, and before we developing functionality in detail, we have tried to create a well-architected project from scratch, with well designed network layer, data layer, asynchronous execution, communication between modules, and last but not least: unit test and integrated testing support. After several months of development, we found some drawbacks of our current architecture, and also found some popular architectures, then I decided to extract our original well designed architecture and make it open-source, with amendment according to the drawbacks and features from the new popular architectures we've found.
Recently I have seen a lot of bootstrap/base Android projects, including [JakeWharton's u2020](https://github.com/JakeWharton/u2020), [mobiwiseco's Android-Base-Project](https://github.com/mobiwiseco/Android-Base-Project), etc, but none of these projects cover all the features I include in this AndroidTDDBootStrap project. That's why I want more people to see this repo, and I also want get feedback from more people to improve this project.

## Architecture
Based on the project architecture I'm currently working on, [YOLO](https://www.yoloyolo.tv/), and inspired by popular architectures: [Android Clean Architecture](https://github.com/android10/Android-CleanArchitecture), [Against Android Unit Tests: The Square way](http://www.philosophicalhacker.com/2015/04/10/against-android-unit-tests/).

![perfect_android_model_layer](http://blog.piasy.com/img/201605/perfect_android_model_layer.png)

+  MVP: [YaMvp](https://github.com/Piasy/YaMvp), Yet another Mvp library. Super simple, but with enough functionality.
+  Dependency injection
  +  [Dagger2](https://github.com/google/dagger), A fast dependency injector for Android and Java.
  +  [ButterKnife](https://github.com/JakeWharton/butterknife), View "injection" library for Android.
+  Model layer, [完美的安卓 model 层架构（上）](http://blog.piasy.com/2016/05/06/Perfect-Android-Model-Layer/)
  +  [OkHttp](http://square.github.io/okhttp/), An HTTP+SPDY client for Android and Java applications.
  +  [Retrofit](http://square.github.io/retrofit/), Type-safe HTTP client for Android and Java by Square, Inc.
  +  [Gson](https://github.com/google/gson), A Java library that can be used to convert Java Objects into their JSON representation.
  +  [SQLBrite](https://github.com/square/sqlbrite), A lightweight wrapper around SQLiteOpenHelper which introduces reactive stream semantics to SQL operations..
  +  [SQLDelight](https://github.com/square/sqldelight), Generates Java models from CREATE TABLE statements.
  +  [AutoValue](https://github.com/google/auto/tree/master/value), Generated immutable value classes for Java 1.6+.
  +  [auto-value-gson](https://github.com/Piasy/auto-value-gson/tree/autogson), AutoValue Extension to add Gson De/Serializer support.
  +  [auto-value-parcel](https://github.com/rharter/auto-value-parcel), An Android Parcelable extension for Google's AutoValue.
+  Reactive programming
  +  [RxJava](https://github.com/ReactiveX/RxJava) – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.
  +  [RxAndroid](https://github.com/ReactiveX/RxAndroid), RxJava bindings for Android.
  +  [RxBinding](https://github.com/JakeWharton/RxBinding), RxJava binding APIs for Android's UI widgets.
  +  [RxLifecycle](https://github.com/trello/RxLifecycle), Lifecycle handling APIs for Android apps using RxJava.
+  Communication between modules: [EventBus](https://github.com/greenrobot/EventBus), Android optimized event bus that simplifies communication between Activities, Fragments, Threads, Services, etc. Less code, better quality.
+  Image loader: [Fresco](https://github.com/facebook/fresco), An Android library for managing images and the memory they use.
+  Other core libraries
  +  [SafelyAndroid](https://github.com/Piasy/SafelyAndroid), Build safely Android app, no more Activity not found error and Activity state loss error!
  +  [RetroLambda](https://github.com/orfjackal/retrolambda), Backport of Java 8's lambda expressions to Java 7, 6 and 5.
  +  [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP), An adaptation of the JSR-310 backport for Android.
  +  [AutoBundle](https://github.com/yatatsu/AutoBundle/releases/tag/1.0.2), AutoBundle generates boilerplate code for field binding with android.os.Bundle.
  +  [FlexLayout](https://github.com/mmin18/FlexLayout), A powerful Android layout view that use java expression in layout params to describe relative positions.
+  Developer tools
  +  [XLog](https://github.com/promeG/XLog), Method call logging based on dexposed.
  +  [LeakCanary](https://github.com/square/leakcanary), A memory leak detection library for Android and Java.
  +  [ANR-WatchDog](https://github.com/SalomonBrys/ANR-WatchDog), A simple watchdog that detects Android ANR (Application Not Responding) error and throws a meaningful exception.
  +  [AndroidPerformanceMonitor](https://github.com/markzhai/AndroidPerformanceMonitor), A transparent ui-block detection library for Android. (known as BlockCanary)
  +  [strictmode-notifier](https://github.com/nshmura/strictmode-notifier), Improving StrictMode's report on Android.
  +  [Timber](https://github.com/JakeWharton/timber), A logger with a small, extensible API which provides utility on top of Android's normal Log class.
  +  [OkHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor), An OkHttp interceptor which logs HTTP request and response data.
  +  [Ok2Curl](https://github.com/mrmike/Ok2Curl), Convert OkHttp requests into curl logs.
  +  [Stetho](https://github.com/facebook/stetho), Stetho is a debug bridge for Android applications, enabling the powerful Chrome Developer Tools and much more.
  +  [android-git-sha-plugin](https://github.com/promeG/android-git-sha-plugin), Automatically add current GIT SHA value to your apk. It can rise an error if the current git branch is dirty.
  +  [Codestyle](https://github.com/Piasy/java-code-styles), Customized base on [Square java-code-styles](https://github.com/square/java-code-styles).
+  Others
  +  [Iconfy](https://github.com/JoanZapata/android-iconify), Android integration of multiple icon providers such as FontAwesome, Entypo, Typicons,...
  +  [Fabric](https://fabric.io/), Crash report.
  +  [Once](https://github.com/jonfinerty/Once), A small Android library to manage one-off operations.
+  Unit test
  +  Junit && Android Junit
  +  Following the [Square Way](http://www.philosophicalhacker.com/2015/04/10/against-android-unit-tests/)
  +  [Android Unmock Gradle Plugin](https://github.com/bjoernQ/unmock-plugin), Gradle plugin to be used in combination with the new unit testing feature of the Gradle Plugin / Android Studio to use real classes for e.g. SparseArray.
  +  [mockito](http://mockito.org/), Tasty mocking framework for unit tests in Java.
  +  [RESTMock](https://github.com/andrzejchm/RESTMock), HTTP Server for Android Instrumentation tests.
  +  [Espresso](https://code.google.com/p/android-test-kit/)
+  Continuous integration
  +  [Travis CI](https://travis-ci.org/)
+  Code quality, customized from [Vincent Brison's vb-android-app-quality repo](https://github.com/vincentbrison/vb-android-app-quality)
  +  [AndroidCodeQualityConfig](https://github.com/Piasy/AndroidCodeQualityConfig)
  +  [Checkstyle](https://github.com/checkstyle/checkstyle), Checkstyle is a development tool to help programmers write Java code that adheres to a coding standard. By default it supports the Google Java Style Guide and Sun Code Conventions, but is highly configurable. It can be invoked with an ANT task and a command line program.
  +  [Find bugs](https://github.com/findbugsproject/findbugs), FindBugs is a defect detection tool for Java that uses static analysis to look for more than 200 bug patterns, such as null pointer dereferences, infinite recursive loops, bad uses of the Java libraries and deadlocks.
  +  [PMD](https://github.com/pmd/pmd), PMD is a source code analyzer.
  +  Lint
+  Code coverage
  +  Jacoco && [Codecov](https://codecov.io)

## Project structure
+  base
  +  The so called architecture part, and base classes, best practice, etc.
+  model
  +  Business related data layer, entities, API, REPO, etc.
+  app
  +  App functionality.
+  package organization
  +  package by layer v.s. package by feature, read more about the [Package organization part of this blog](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/), and [Package by feature, not layer](http://www.javapractices.com/topic/TopicAction.do?Id=205).
  +  package by layer + package by feature
    +  network API, data object, REPO are organized in the single `model` library module, but inside model module, classes are organized by feature
    +  app functionality are organized by feature, mvp, di, ui code are organized together

## Dev tips
+  Create Activity
  +  **TODO** use the MVP feature generator
+  Unit test
  +  use the check*.sh script in buildsystem dir
+  Run `./buildsystem/ci.sh` before git push.

## Todo
+  ~~CheckStyle~~
+  ~~re-arch the provider package~~
+  ~~Espresso test of presentation module~~
+  ~~refactor AppComponent~~
+  ~~facebook [BUCK](http://buckbuild.com) integration~~ integrate with [OkBuck](https://github.com/Piasy/OkBuck)
+  ~~Update dependencies~~
+  ~~refactor modules~~
+  ~~NDK integrate~~ now it's easy to integrate NDK with Android gradle build tools 2.2.2
+  MVP source generator plugin
+  MVVM branch
+  react native branch
+  kotlin branch

## Coverage
![codecov.io](http://codecov.io/github/Piasy/AndroidTDDBootStrap/branch.svg?branch=master)

## Acknowledgement
+  Thanks for our team, [YOLO](https://www.yoloyolo.tv/).
+  Thanks for my colleague & mentor, [promeG](https://github.com/promeG/).
