package com.github.pwittchen.swipes.library;

import android.view.MotionEvent;
import rx.Observable;
import rx.Subscriber;

public class Swipes {

  private SwipeListener swipeListener;
  private Subscriber<? super SwipeEvent> subscriber;
  /**
   * Threshold is added for neglecting swiping
   * when differences between changed x or y coordinates are too small
   */
  private final static int SWIPING_THRESHOLD = 20;
  private final static int SWIPED_THRESHOLD = 100;
  private float xDown, xUp;
  private float yDown, yUp;
  private float xMove, yMove;

  public void addListener(SwipeListener swipeListener) {
    checkNotNull(swipeListener, "swipeListener == null");
    this.swipeListener = swipeListener;
  }

  public Observable<SwipeEvent> observe() {
    this.swipeListener = createReactiveSwipeListener();
    return Observable.create(new Observable.OnSubscribe<SwipeEvent>() {
      @Override public void call(final Subscriber<? super SwipeEvent> subscriber) {
        Swipes.this.subscriber = subscriber;
      }
    });
  }

  public void onTouchEvent(final MotionEvent event) {
    checkNotNull(event, "event == null");
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:   // user started touching the screen
        onActionDown(event);
        break;
      case MotionEvent.ACTION_UP:     // user stopped touching the screen
        onActionUp(event);
        break;
      case MotionEvent.ACTION_MOVE:   // user is moving finger on the screen
        onActionMove(event);
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
    final boolean swipedHorizontally = Math.abs(xUp - xDown) > SWIPED_THRESHOLD;
    final boolean swipedVertically = Math.abs(yUp - yDown) > SWIPED_THRESHOLD;

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
    final boolean isSwipingHorizontally = Math.abs(xMove - xDown) > SWIPING_THRESHOLD;
    final boolean isSwipingVertically = Math.abs(yMove - yDown) > SWIPING_THRESHOLD;

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
    if (subscriber != null) {
      subscriber.onNext(swipingLeft);
    }
  }

  private void checkNotNull(final Object object, final String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }
}
