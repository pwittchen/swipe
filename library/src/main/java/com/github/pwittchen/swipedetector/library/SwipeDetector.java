package com.github.pwittchen.swipedetector.library;

import android.view.MotionEvent;

public class SwipeDetector {

    private SwipeListener swipeListener;
    /**
     * Threshold is added for neglecting swiping
     * when differences between changed x or y coordinates are too small
     */
    private final static int SWIPING_THRESHOLD = 20;
    private final static int SWIPED_THRESHOLD = 100;
    private float xDown, xUp;
    private float yDown, yUp;
    private float xMove, yMove;

    public interface SwipeListener {
        void onSwipingLeft(MotionEvent event);

        void onSwipedLeft(MotionEvent event);

        void onSwipingRight(MotionEvent event);

        void onSwipedRight(MotionEvent event);

        void onSwipingUp(MotionEvent event);

        void onSwipedUp(MotionEvent event);

        void onSwipingDown(MotionEvent event);

        void onSwipedDown(MotionEvent event);
    }

    public SwipeDetector(SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    public void onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   // user started touching the screen
                xDown = event.getX();
                yDown = event.getY();
                break;
            case MotionEvent.ACTION_UP:     // user stopped touching the screen
                xUp = event.getX();
                yUp = event.getY();
                boolean swipedHorizontally = Math.abs(xUp - xDown) > SWIPED_THRESHOLD;
                boolean swipedVertically = Math.abs(yUp - yDown) > SWIPED_THRESHOLD;

                if (swipedHorizontally) {
                    boolean swipedRight = xUp > xDown;
                    boolean swipedLeft = xUp < xDown;
                    if (swipedRight) {
                        swipeListener.onSwipedRight(event);
                    }
                    if (swipedLeft) {
                        swipeListener.onSwipedLeft(event);
                    }
                }

                if (swipedVertically) {
                    boolean swipedDown = yDown < yUp;
                    boolean swipedUp = yDown > yUp;
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
                boolean isSwipingHorizontally = Math.abs(xMove - xDown) > SWIPING_THRESHOLD;
                boolean isSwipingVertically = Math.abs(yMove - yDown) > SWIPING_THRESHOLD;
                if (isSwipingHorizontally) {
                    boolean isSwipingRight = xMove > xDown;
                    boolean isSwipingLeft = xMove < xDown;
                    if (isSwipingRight) {
                        swipeListener.onSwipingRight(event);
                    }
                    if (isSwipingLeft) {
                        swipeListener.onSwipingLeft(event);
                    }
                }

                if (isSwipingVertically) {
                    boolean isSwipingDown = yDown < yMove;
                    boolean isSwipingUp = yDown > yMove;
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
