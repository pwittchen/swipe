package com.github.pwittchen.swipedetector.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import com.github.pwittchen.swipedetector.library.SwipeDetector;
import com.github.pwittchen.swipedetector.library.SwipeListener;
import pwittchen.com.swipedetector.R;

public class SwipeDetectorActivity extends AppCompatActivity {

  protected TextView info;
  private SwipeDetector swipeDetector;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    info = (TextView) findViewById(R.id.info);
    swipeDetector = new SwipeDetector(createSwipeListener());
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
        Intent intent = new Intent(this, SwipeDetectorReactiveActivity.class);
        startActivity(intent);
        break;
    }
    return true;
  }

  @Override public boolean dispatchTouchEvent(MotionEvent event) {
    swipeDetector.onTouchEvent(event);
    return super.dispatchTouchEvent(event);
  }

  private SwipeListener createSwipeListener() {
    return new SwipeListener() {
      @Override public void onSwipingLeft(final MotionEvent event) {
        info.setText("SWIPING_LEFT");
      }

      @Override public void onSwipedLeft(final MotionEvent event) {
        info.setText("SWIPED_LEFT");
      }

      @Override public void onSwipingRight(final MotionEvent event) {
        info.setText("SWIPING_RIGHT");
      }

      @Override public void onSwipedRight(final MotionEvent event) {
        info.setText("SWIPED_RIGHT");
      }

      @Override public void onSwipingUp(final MotionEvent event) {
        info.setText("SWIPING_UP");
      }

      @Override public void onSwipedUp(final MotionEvent event) {
        info.setText("SWIPED_UP");
      }

      @Override public void onSwipingDown(final MotionEvent event) {
        info.setText("swiping down");
      }

      @Override public void onSwipedDown(final MotionEvent event) {
        info.setText("swiped down");
      }
    };
  }
}
