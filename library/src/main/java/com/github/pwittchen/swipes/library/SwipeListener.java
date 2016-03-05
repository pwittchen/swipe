package com.github.pwittchen.swipes.library;

import android.view.MotionEvent;

public interface SwipeListener {
  void onSwipingLeft(final MotionEvent event);

  void onSwipedLeft(final MotionEvent event);

  void onSwipingRight(final MotionEvent event);

  void onSwipedRight(final MotionEvent event);

  void onSwipingUp(final MotionEvent event);

  void onSwipedUp(final MotionEvent event);

  void onSwipingDown(final MotionEvent event);

  void onSwipedDown(final MotionEvent event);
}
