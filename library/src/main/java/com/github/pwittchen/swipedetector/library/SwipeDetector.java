package com.github.pwittchen.swipedetector.library;

import android.view.MotionEvent;

public class SwipeDetector {

    private final SwipeListener swipeListener;
    /**
     * Threshold is added for neglecting swiping
     * when differences between changed x or y coordinates are too small
     */
    private final static int SWIPING_THRESHOLD = 20;
    private final static int SWIPED_THRESHOLD = 100;
    private float xDown, xUp;
    private float yDown, yUp;
    private float xMove, yMove;

    public SwipeDetector(final SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    public void onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   // user started touching the screen
                xDown = event.getX();
                yDown = event.getY();
                break;
            case MotionEvent.ACTION_UP:     // user stopped touching the screen
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
                break;
            case MotionEvent.ACTION_MOVE:   // user is moving finger on the screen
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
                break;
        }
    }

}
