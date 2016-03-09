swipe [![Build Status](https://travis-ci.org/pwittchen/swipe.svg?branch=master)](https://travis-ci.org/pwittchen/swipe) ![Maven Central](https://img.shields.io/maven-central/v/com.github.pwittchen/swipe.svg?style=flat)
=====

detects swipe events on Android with listener and RxJava Observable

JavaDoc is available at: http://pwittchen.github.io/swipe/

![swipe gestures](art/swipe.png)

Check out an exemplary **[animation](#example)**!

Contents
--------
- [Usage](#usage)
  - [Imperative way - Listener](#imperative-way---listener)
  - [Reactive way - RxJava](#reactive-way---rxjava)
- [Example](#example)
- [Download](#download)
- [Tests](#tests)
- [Code style](#code-style)
- [Static code analysis](#static-code-analysis)
- [References](#references)
- [License](#license)

Usage
-----

### Imperative way - Listener

**Step 1**: Create `Swipe` attribute in the `Activiy`:

```java
private Swipe swipe;
```

**Step 2**: Initialize `Swipe` object and add listener:

```java
@Override protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  
  setContentView(R.layout.activity_main);
  info = (TextView) findViewById(R.id.info);
  
  swipe = new Swipe();
  swipe.addListener(new SwipeListener() {
    @Override public void onSwipingLeft(final MotionEvent event) {
      info.setText("SWIPING_LEFT");
    }

    @Override public void onSwipedLeft(final MotionEvent event) {
      info.setText("SWIPED_LEFT");
    }

    @Override public void onSwipingRight(final MotionEvent event) {
      info.setText("SWIPING_RIGHT");
    }

    @Override public void onSwipedRight(final MotionEvent event) {
      info.setText("SWIPED_RIGHT");
    }

    @Override public void onSwipingUp(final MotionEvent event) {
      info.setText("SWIPING_UP");
    }

    @Override public void onSwipedUp(final MotionEvent event) {
      info.setText("SWIPED_UP");
    }

    @Override public void onSwipingDown(final MotionEvent event) {
      info.setText("SWIPING_DOWN");
    }

    @Override public void onSwipedDown(final MotionEvent event) {
      info.setText("SWIPED_DOWN");
    }
  });
}
```

**Step 3**: override `dispatchTouchEvent(MotionEvent event)`:

```java
@Override public boolean dispatchTouchEvent(MotionEvent event) {
  swipe.dispatchTouchEvent(event);
  return super.dispatchTouchEvent(event);
}
```

### Reactive way - RxJava

**Step 1**: Create `Swipe` attribute and `Subscription` in the `Activiy`:

```java
private Swipe swipe;
private Subscription subscription;
```

**Step 2**: Initialize `Swipe` object and subscribe `Observable`:

```java
@Override protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  info = (TextView) findViewById(R.id.info);
  
  swipe = new Swipe();
  
  subscription = swipe.observe()
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Action1<SwipeEvent>() {
        @Override public void call(final SwipeEvent swipeEvent) {
          info.setText(swipeEvent.toString());
        }
      });
}
```

**Step 3**: override `dispatchTouchEvent(MotionEvent event)`:

```java
@Override public boolean dispatchTouchEvent(MotionEvent event) {
  swipe.dispatchTouchEvent(event);
  return super.dispatchTouchEvent(event);
}
```

Example
-------

Exemplary application is located in `app` directory of this repository.

If you would like to know, how to use this library with Kotlin, check `app-kotlin` directory in this repository.

Below, you can see an animation presenting how sample application works.

![swipe gestures](art/swipe-animation.gif)

Download
--------

You can depend on the library through Maven:

```xml
<dependency>
    <groupId>com.github.pwittchen</groupId>
    <artifactId>swipe</artifactId>
    <version>0.0.1</version>
</dependency>
```

or through Gradle:

```groovy
dependencies {
  compile 'com.github.pwittchen:swipe:0.0.1'
}
```

Tests
-----

To execute unit tests run:

```
./gradlew test
```

Code style
----------

Code style used in the project is called `SquareAndroid` from Java Code Styles repository by Square available at: https://github.com/square/java-code-styles.

Static code analysis
--------------------

Static code analysis runs Checkstyle, FindBugs, PMD and Lint. It can be executed with command:

 ```
 ./gradlew check
 ```

Reports from analysis are generated in `library/build/reports/` directory.

References
----------

- [better gesture detector project](https://github.com/Polidea/better-gesture-detector)
- [detecting swipe gesture in mobile application](http://blog.wittchen.biz.pl/detecting-swipe-gesture-in-mobile-application/)
- [dispatchTouchEvent(event) method in documentation](http://developer.android.com/reference/android/view/ViewGroup.html#dispatchTouchEvent(android.view.MotionEvent))
- [MotionEvent class in documentation](http://developer.android.com/reference/android/view/MotionEvent.html)

License
-------

    Copyright 2015 Piotr Wittchen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
