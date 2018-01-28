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
package com.github.pwittchen.swipe.library.rx2;

import android.view.MotionEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import io.reactivex.internal.fuseable.ScalarCallable;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class SwipeTest {

  private static final float MIN_MOTION_CHANGE = 1.0f;
  private Swipe swipe;
  private SwipeEvent swipeEvent;
  @Mock private MotionEvent motionEventDown;
  @Mock private MotionEvent motionEventMove;
  @Mock private MotionEvent motionEventUp;
  @Mock private ScalarCallable<Boolean> swipeEventTarget;

  private Boolean isEventDownConsumed;
  private Boolean isEventMoveConsumed;
  private Boolean isEventUpConsumed;

  @Before public void setUp() {
    swipe = new Swipe();
    swipe.setListener(new SwipeListener() {
      @Override public void onSwipingLeft(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPING_LEFT;
      }

      @Override public boolean onSwipedLeft(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPED_LEFT;
        return swipeEventTarget.call();
      }

      @Override public void onSwipingRight(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPING_RIGHT;
      }

      @Override public boolean onSwipedRight(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPED_RIGHT;
        return swipeEventTarget.call();
      }

      @Override public void onSwipingUp(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPING_UP;
      }

      @Override public boolean onSwipedUp(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPED_UP;
        return swipeEventTarget.call();
      }

      @Override public void onSwipingDown(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPING_DOWN;
      }

      @Override public boolean onSwipedDown(MotionEvent event) {
        swipeEvent = SwipeEvent.SWIPED_DOWN;
        return swipeEventTarget.call();
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
    simulateSwipingHorizontally(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPING_RIGHT);
  }

  @Test public void shouldSwipingLeft() throws Exception {
    simulateSwipingHorizontally(-swipe.getSwipingThreshold() - MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPING_LEFT);
  }

  private void simulateSwipingHorizontally(float xMoveIncrement) {
    simulateSwipingHorizontally(xMoveIncrement, false);
  }

  private void simulateSwipingHorizontally(float xMoveIncrement, boolean isEventConsumed) {
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

    when(swipeEventTarget.call()).thenReturn(isEventConsumed);

    // when

    // simulate beginning of touching the screen
    isEventDownConsumed = swipe.dispatchTouchEvent(motionEventDown);
    // simulate finger move on the screen
    isEventMoveConsumed = swipe.dispatchTouchEvent(motionEventMove);

    // then perform assertion in a concrete test
  }

  @Test public void shouldSwipingDown() throws Exception {
    simulateSwipingVertically(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPING_DOWN);
  }

  @Test public void shouldSwipingUp() throws Exception {
    simulateSwipingVertically(-swipe.getSwipingThreshold() - MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPING_UP);
  }

  private void simulateSwipingVertically(float yMoveIncrement) {
    simulateSwipingVertically(yMoveIncrement, false);
  }

  private void simulateSwipingVertically(float yMoveIncrement, boolean isEventConsumed) {
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

    when(swipeEventTarget.call()).thenReturn(isEventConsumed);

    // when

    // simulate beginning of touching the screen
    isEventDownConsumed = swipe.dispatchTouchEvent(motionEventDown);
    // simulate finger move on the screen
    isEventMoveConsumed = swipe.dispatchTouchEvent(motionEventMove);

    // then perform assertion in a concrete test
  }

  @Test public void shouldSwipedRight() {
    simulateSwipedHorizontally(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPED_RIGHT);
  }

  @Test public void shouldSwipedLeft() {
    simulateSwipedHorizontally(-swipe.getSwipedThreshold() - MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPED_LEFT);
  }

  private void simulateSwipedHorizontally(float xUpIncrement) {
    simulateSwipedHorizontally(xUpIncrement, false);
  }

  private void simulateSwipedHorizontally(float xUpIncrement, boolean isEventConsumed) {
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

    when(swipeEventTarget.call()).thenReturn(isEventConsumed);

    // when

    // simulate beginning of touching the screen
    isEventDownConsumed = swipe.dispatchTouchEvent(motionEventDown);
    // simulate finger moved and stopped touching screen
    isEventUpConsumed = swipe.dispatchTouchEvent(motionEventUp);

    // then perform assertion in a concrete test
  }

  @Test public void shouldSwipedDown() {
    simulateSwipedVertically(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPED_DOWN);
  }

  @Test public void shouldSwipedUp() {
    simulateSwipedVertically(-swipe.getSwipedThreshold() - MIN_MOTION_CHANGE);
    assertThat(swipeEvent).isEqualTo(SwipeEvent.SWIPED_UP);
  }

  private void simulateSwipedVertically(float yUpIncrement) {
    simulateSwipedVertically(yUpIncrement, false);
  }

  private void simulateSwipedVertically(float yUpIncrement, boolean isEventConsumed) {
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

    when(swipeEventTarget.call()).thenReturn(isEventConsumed);

    // when

    // simulate beginning of touching the screen
    isEventDownConsumed = swipe.dispatchTouchEvent(motionEventDown);
    // simulate finger moved and stopped touching screen
    isEventUpConsumed = swipe.dispatchTouchEvent(motionEventUp);

    // then perform assertion in a concrete test
  }

  @Test public void shouldNotConsumeSwipingHorizontallyMotionEventDown() {
    simulateSwipingHorizontally(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, false);
    assertThat(isEventDownConsumed).isFalse();

    simulateSwipingHorizontally(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, true);
    assertThat(isEventDownConsumed).isFalse();
  }

  @Test public void shouldNotConsumeSwipingVerticallyMotionEventDown() {
    simulateSwipingVertically(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, false);
    assertThat(isEventDownConsumed).isFalse();

    simulateSwipingVertically(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, true);
    assertThat(isEventDownConsumed).isFalse();
  }

  @Test public void shouldNotConsumeSwipingHorizontallyMotionEventMove() {
    simulateSwipingHorizontally(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, false);
    assertThat(isEventMoveConsumed).isFalse();

    simulateSwipingHorizontally(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, true);
    assertThat(isEventMoveConsumed).isFalse();
  }

  @Test public void shouldNotConsumeSwipingVerticallyMotionEventMove() {
    simulateSwipingVertically(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, false);
    assertThat(isEventMoveConsumed).isFalse();

    simulateSwipingVertically(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, true);
    assertThat(isEventMoveConsumed).isFalse();
  }

  @Test public void shouldIgnoreSwipingHorizontallyMotionEventUp() {
    simulateSwipingHorizontally(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, false);
    //MotionEvent.ACTION_UP is not used in simulation, assuming result should be null
    assertThat(isEventUpConsumed).isNull();

    simulateSwipingHorizontally(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, true);
    //MotionEvent.ACTION_UP is not used in simulation, assuming result should be null
    assertThat(isEventUpConsumed).isNull();
  }

  @Test public void shouldIgnoreSwipingVerticallyMotionEventUp() {
    simulateSwipingVertically(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, false);
    //MotionEvent.ACTION_UP is not used in simulation, assuming result should be null
    assertThat(isEventUpConsumed).isNull();

    simulateSwipingVertically(swipe.getSwipingThreshold() + MIN_MOTION_CHANGE, true);
    //MotionEvent.ACTION_UP is not used in simulation, assuming result should be null
    assertThat(isEventUpConsumed).isNull();
  }

  @Test public void shouldNotConsumeSwipedHorizontallyMotionEventDown() {
    simulateSwipedHorizontally(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, false);
    assertThat(isEventDownConsumed).isFalse();

    simulateSwipedHorizontally(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, true);
    assertThat(isEventDownConsumed).isFalse();
  }

  @Test public void shouldNotConsumeSwipedVerticallyMotionEventDown() {
    simulateSwipedVertically(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, false);
    assertThat(isEventDownConsumed).isFalse();

    simulateSwipedVertically(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, true);
    assertThat(isEventDownConsumed).isFalse();
  }

  @Test public void shouldConsumeSwipedHorizontallyMotionEventUp() {
    simulateSwipedHorizontally(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, false);
    assertThat(isEventUpConsumed).isFalse();

    simulateSwipedHorizontally(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, true);
    assertThat(isEventUpConsumed).isTrue();
  }

  @Test public void shouldConsumeSwipedVerticallyMotionEventUp() {
    simulateSwipedVertically(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, false);
    assertThat(isEventUpConsumed).isFalse();

    simulateSwipedVertically(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, true);
    assertThat(isEventUpConsumed).isTrue();
  }

  @Test public void shouldIgnoreSwipedHorizontallyMotionEventMove() {
    simulateSwipedHorizontally(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, false);
    //MotionEvent.ACTION_MOVE is not used in simulation, assuming result should be null
    assertThat(isEventMoveConsumed).isNull();

    simulateSwipedHorizontally(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, true);
    //MotionEvent.ACTION_MOVE is not used in simulation, assuming result should be null
    assertThat(isEventMoveConsumed).isNull();
  }

  @Test public void shouldIgnoreSwipedVerticallyMotionEventMove() {
    simulateSwipedVertically(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, false);
    //MotionEvent.ACTION_MOVE is not used in simulation, assuming result should be null
    assertThat(isEventMoveConsumed).isNull();

    simulateSwipedVertically(swipe.getSwipedThreshold() + MIN_MOTION_CHANGE, true);
    //MotionEvent.ACTION_MOVE is not used in simulation, assuming result should be null
    assertThat(isEventMoveConsumed).isNull();
  }
}