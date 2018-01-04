CHANGELOG
=========

v. 0.2.0
--------
*04 Jan 2018*

- migrated library to RxJava2.x as a separate artifact on a separate Git branch
- removed `master` branch from the repo
- updated project dependencies
- updated Gradle to 3.x
- added Retrolambda to sample Java app

v. 0.1.0
--------
*04 May 2017*

- updated project dependencies
- updated Gradle configuration
- updated sample apps
- renamed `com.github.pwittchen.swipe.library.Swipe#addListener` to `com.github.pwittchen.swipe.library.Swipe#setListener` - issue #14
- added `Swipe(int swipingThreshold, int swipedThreshold)` constructor, which allows to configure swiping and swiped threshold/sensitivity - fixes #17

v. 0.0.1
--------
*06 Mar 2015*

First release of the library.
