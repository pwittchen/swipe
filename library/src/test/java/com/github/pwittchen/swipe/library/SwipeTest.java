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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class SwipeTest {

  private static final float MIN_MOTION_CHANGE = 1.0f;
  private Swipe swipe;
  private SwipeEvent swipeEvent;
  @Mock private MotionEvent motionEventDown;
  @Mock private MotionEvent motionEventMove;
  @Mock private MotionEvent motionEventUp;

  @Before public void setUp() {
    swipe = new Swipe();
    swipe.setListener(new SwipeListener() {
      @Override public void onSwipingLeft(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPING_LEFT;
      }

      @Override public void onSwipedLeft(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPED_LEFT;
      }

      @Override public void onSwipingRight(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPING_RIGHT;
      }

      @Override public void onSwipedRight(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPED_RIGHT;
      }

      @Override public void onSwipingUp(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPING_UP;
      }

      @Override public void onSwipedUp(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPED_UP;
      }

      @Override public void onSwipingDown(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPING_DOWN;
      }

      @Override public void onSwipedDown(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPED_DOWN;
      }
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowAnExceptionWhenListenerIsNull() {
    swipe.setListener(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowAnExceptionWhenMotionEventIsNull() {
    swipe.dispatchTouchEvent(null);
  }

  @Test public void shouldSwipingRight() throws Exception {
    simulateSwipingHorizontally(swipe.SWIPING_THRESHOLD + MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPING_RIGHT);
  }

  @Test public void shouldSwipingLeft() throws Exception {
    simulateSwipingHorizontally(-swipe.SWIPING_THRESHOLD - MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPING_LEFT);
  }

  private void simulateSwipingHorizontally(float xMoveIncrement) {
    // given
    final float xDown = 1.0f;
    final float yDown = 1.0f;
    when(motionEventDown.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
    when(motionEventDown.getX()).thenReturn(xDown);
    when(motionEventDown.getY()).thenReturn(yDown);

    final float xMove = xDown + xMoveIncrement;
    final float yMove = 1.0f;
    when(motionEventMove.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
    when(motionEventMove.getX()).thenReturn(xMove);
    when(motionEventMove.getY()).thenReturn(yMove);

    // when
    swipe.dispatchTouchEvent(motionEventDown); // simulate beginning of touching the screen
    swipe.dispatchTouchEvent(motionEventMove); // simulate finger move on the screen

    // then perform assertion in a concrete test
  }

  @Test public void shouldSwipingDown() throws Exception {
    simulateSwipingVertically(swipe.SWIPING_THRESHOLD + MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPING_DOWN);
  }

  @Test public void shouldSwipingUp() throws Exception {
    simulateSwipingVertically(-swipe.SWIPING_THRESHOLD - MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPING_UP);
  }

  private void simulateSwipingVertically(float yMoveIncrement) {
    // given
    final float xDown = 1.0f;
    final float yDown = 1.0f;
    when(motionEventDown.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
    when(motionEventDown.getX()).thenReturn(xDown);
    when(motionEventDown.getY()).thenReturn(yDown);

    final float xMove = 1.0f;
    final float yMove = yDown + yMoveIncrement;
    when(motionEventMove.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
    when(motionEventMove.getX()).thenReturn(xMove);
    when(motionEventMove.getY()).thenReturn(yMove);

    // when
    swipe.dispatchTouchEvent(motionEventDown); // simulate beginning of touching the screen
    swipe.dispatchTouchEvent(motionEventMove); // simulate finger move on the screen

    // then perform assertion in a concrete test
  }

  @Test public void shouldSwipedRight() {
    simulateSwipedHorizontally(swipe.SWIPED_THRESHOLD + MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPED_RIGHT);
  }

  @Test public void shouldSwipedLeft() {
    simulateSwipedHorizontally(-swipe.SWIPED_THRESHOLD - MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPED_LEFT);
  }

  private void simulateSwipedHorizontally(float xUpIncrement) {
    // given
    final float xDown = 1.0f;
    final float yDown = 1.0f;
    when(motionEventDown.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
    when(motionEventDown.getX()).thenReturn(xDown);
    when(motionEventDown.getY()).thenReturn(yDown);

    final float xUp = xDown + xUpIncrement;
    final float yUp = 1.0f;
    when(motionEventUp.getAction()).thenReturn(MotionEvent.ACTION_UP);
    when(motionEventUp.getX()).thenReturn(xUp);
    when(motionEventUp.getY()).thenReturn(yUp);

    // when
    swipe.dispatchTouchEvent(motionEventDown); // simulate beginning of touching the screen
    swipe.dispatchTouchEvent(motionEventUp);   // simulate finger moved and stopped touching screen

    // then perform assertion in a concrete test
  }

  @Test public void shouldSwipedDown() {
    simulateSwipedVertically(swipe.SWIPED_THRESHOLD + MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPED_DOWN);
  }

  @Test public void shouldSwipedUp() {
    simulateSwipedVertically(-swipe.SWIPED_THRESHOLD - MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPED_UP);
  }

  private void simulateSwipedVertically(float yUpIncrement) {
    // given
    final float xDown = 1.0f;
    final float yDown = 1.0f;
    when(motionEventDown.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
    when(motionEventDown.getX()).thenReturn(xDown);
    when(motionEventDown.getY()).thenReturn(yDown);

    final float xUp = 1.0f;
    final float yUp = yDown + yUpIncrement;
    when(motionEventUp.getAction()).thenReturn(MotionEvent.ACTION_UP);
    when(motionEventUp.getX()).thenReturn(xUp);
    when(motionEventUp.getY()).thenReturn(yUp);

    // when
    swipe.dispatchTouchEvent(motionEventDown);  // simulate beginning of touching the screen
    swipe.dispatchTouchEvent(motionEventUp);    // simulate finger moved and stopped touching screen

    // then perform assertion in a concrete test
  }
}