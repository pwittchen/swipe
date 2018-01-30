/*
 * Copyright (C) 2018 Piotr Wittchen
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

public abstract class SimpleSwipeListener implements SwipeListener {
  @Override public void onSwipingLeft(MotionEvent event) {
  }

  @Override public boolean onSwipedLeft(MotionEvent event) {
    return false;
  }

  @Override public void onSwipingRight(MotionEvent event) {
  }

  @Override public boolean onSwipedRight(MotionEvent event) {
    return false;
  }

  @Override public void onSwipingUp(MotionEvent event) {
  }

  @Override public boolean onSwipedUp(MotionEvent event) {
    return false;
  }

  @Override public void onSwipingDown(MotionEvent event) {
  }

  @Override public boolean onSwipedDown(MotionEvent event) {
    return false;
  }
}