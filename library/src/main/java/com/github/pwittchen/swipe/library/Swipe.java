/*
 * Copyright (C) 2016 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.swipe.library;

import android.view.MotionEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Allows to detect swipe events through listener or with RxJava Observables
 */
public class Swipe {

  /**
   * Swiping threshold is added for neglecting swiping
   * when differences between changed x or y coordinates are too small
   */
  public final static int DEFAULT_SWIPING_THRESHOLD = 20;
  /**
   * Swiped threshold is added for neglecting swiping
   * when differences between changed x or y coordinates are too small
   */
  public final static int DEFAULT_SWIPED_THRESHOLD = 100;

  private final int swipingThreshold;
  private final int swipedThreshold;

  private SwipeListener swipeListener;
  private ObservableEmitter<SwipeEvent> emitter;
  private float xDown, xUp;
  private float yDown, yUp;
  private float xMove, yMove;

  public Swipe() {
    this(DEFAULT_SWIPING_THRESHOLD, DEFAULT_SWIPED_THRESHOLD);
  }

  public Swipe(int swipingThreshold, int swipedThreshold) {
    this.swipingThreshold = swipingThreshold;
    this.swipedThreshold = swipedThreshold;
  }

  /**
   * Adds listener for swipe events.
   * Remember to call {@link #dispatchTouchEvent(MotionEvent) dispatchTouchEvent} method as well.
   *
   * @param swipeListener listener
   */
  public void setListener(SwipeListener swipeListener) {
    checkNotNull(swipeListener, "swipeListener == null");
    this.swipeListener = swipeListener;
  }

  /**
   * Observes swipe events with RxJava Observable.
   * Remember to call {@link #dispatchTouchEvent(MotionEvent) dispatchTouchEvent} method as well.
   *
   * @return Observable<SwipeEvent> observable with stream of swipe events
   */
  public Observable<SwipeEvent> observe() {
    this.swipeListener = createReactiveSwipeListener();
    //return Observable.unsafeCreate(new Observable.OnSubscribe<SwipeEvent>() {
    //  @Override public void call(final Subscriber<? super SwipeEvent> subscriber) {
    //    Swipe.this.subscriber = subscriber;
    //  }
    //});

    return Observable.create(new ObservableOnSubscribe<SwipeEvent>() {
      @Override public void subscribe(ObservableEmitter<SwipeEvent> emitter) throws Exception {
        Swipe.this.emitter = emitter;
      }
    });
  }

  /**
   * Called to process touch screen events.
   *
   * @param event MotionEvent
   */
  public void dispatchTouchEvent(final MotionEvent event) {
    checkNotNull(event, "event == null");
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN: // user started touching the screen
        onActionDown(event);
        break;
      case MotionEvent.ACTION_UP:   // user stopped touching the screen
        onActionUp(event);
        break;
      case MotionEvent.ACTION_MOVE: // user is moving finger on the screen
        onActionMove(event);
        break;
      default:
        break;
    }
  }

  private void onActionDown(final MotionEvent event) {
    xDown = event.getX();
    yDown = event.getY();
  }

  private void onActionUp(final MotionEvent event) {
    xUp = event.getX();
    yUp = event.getY();
    final boolean swipedHorizontally = Math.abs(xUp - xDown) > getSwipedThreshold();
    final boolean swipedVertically = Math.abs(yUp - yDown) > getSwipedThreshold();

    if (swipedHorizontally) {
      final boolean swipedRight = xUp > xDown;
      final boolean swipedLeft = xUp < xDown;

      if (swipedRight) {
        swipeListener.onSwipedRight(event);
      }
      if (swipedLeft) {
        swipeListener.onSwipedLeft(event);
      }
    }

    if (swipedVertically) {
      final boolean swipedDown = yDown < yUp;
      final boolean swipedUp = yDown > yUp;
      if (swipedDown) {
        swipeListener.onSwipedDown(event);
      }
      if (swipedUp) {
        swipeListener.onSwipedUp(event);
      }
    }
  }

  private void onActionMove(final MotionEvent event) {
    xMove = event.getX();
    yMove = event.getY();
    final boolean isSwipingHorizontally = Math.abs(xMove - xDown) > getSwipingThreshold();
    final boolean isSwipingVertically = Math.abs(yMove - yDown) > getSwipingThreshold();

    if (isSwipingHorizontally) {
      final boolean isSwipingRight = xMove > xDown;
      final boolean isSwipingLeft = xMove < xDown;

      if (isSwipingRight) {
        swipeListener.onSwipingRight(event);
      }
      if (isSwipingLeft) {
        swipeListener.onSwipingLeft(event);
      }
    }

    if (isSwipingVertically) {
      final boolean isSwipingDown = yDown < yMove;
      final boolean isSwipingUp = yDown > yMove;

      if (isSwipingDown) {
        swipeListener.onSwipingDown(event);
      }
      if (isSwipingUp) {
        swipeListener.onSwipingUp(event);
      }
    }
  }

  private SwipeListener createReactiveSwipeListener() {
    return new SwipeListener() {
      @Override public void onSwipingLeft(MotionEvent event) {
        onNextSafely(SwipeEvent.SWIPING_LEFT);
      }

      @Override public void onSwipedLeft(MotionEvent event) {
        onNextSafely(SwipeEvent.SWIPED_LEFT);
      }

      @Override public void onSwipingRight(MotionEvent event) {
        onNextSafely(SwipeEvent.SWIPING_RIGHT);
      }

      @Override public void onSwipedRight(MotionEvent event) {
        onNextSafely(SwipeEvent.SWIPED_RIGHT);
      }

      @Override public void onSwipingUp(MotionEvent event) {
        onNextSafely(SwipeEvent.SWIPING_UP);
      }

      @Override public void onSwipedUp(MotionEvent event) {
        onNextSafely(SwipeEvent.SWIPED_UP);
      }

      @Override public void onSwipingDown(MotionEvent event) {
        onNextSafely(SwipeEvent.SWIPING_DOWN);
      }

      @Override public void onSwipedDown(MotionEvent event) {
        onNextSafely(SwipeEvent.SWIPED_DOWN);
      }
    };
  }

  private void onNextSafely(final SwipeEvent swipingLeft) {
    if (emitter != null) {
      emitter.onNext(swipingLeft);
    }
  }

  private void checkNotNull(final Object object, final String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  public int getSwipingThreshold() {
    return swipingThreshold;
  }

  public int getSwipedThreshold() {
    return swipedThreshold;
  }
}
