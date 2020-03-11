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
package com.github.pwittchen.swipe.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.github.pwittchen.swipe.library.rx2.SwipeListener;
import pwittchen.com.swipedetector.R;

public class SwipeActivity extends AppCompatActivity {

  protected TextView info;
  private Swipe swipe;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    info = (TextView) findViewById(R.id.info);
    swipe = new Swipe();
    swipe.setListener(new SwipeListener() {
      @Override public void onSwipingLeft(final MotionEvent event) {
        info.setText("SWIPING_LEFT");
      }

      @Override public boolean onSwipedLeft(final MotionEvent event) {
        info.setText("SWIPED_LEFT");
        return false;
      }

      @Override public void onSwipingRight(final MotionEvent event) {
        info.setText("SWIPING_RIGHT");
      }

      @Override public boolean onSwipedRight(final MotionEvent event) {
        info.setText("SWIPED_RIGHT");
        return false;
      }

      @Override public void onSwipingUp(final MotionEvent event) {
        info.setText("SWIPING_UP");
      }

      @Override public boolean onSwipedUp(final MotionEvent event) {
        info.setText("SWIPED_UP");
        return false;
      }

      @Override public void onSwipingDown(final MotionEvent event) {
        info.setText("SWIPING_DOWN");
      }

      @Override public boolean onSwipedDown(final MotionEvent event) {
        info.setText("SWIPED_DOWN");
        return false;
      }
    });
  }

  @Override public boolean dispatchTouchEvent(MotionEvent event) {
    return swipe.dispatchTouchEvent(event) || super.dispatchTouchEvent(event);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.listener:
        break;
      case R.id.rx:
        final Intent intent = new Intent(this, SwipeRxActivity.class);
        startActivity(intent);
        break;
    }
    return true;
  }
}
