package com.github.pwittchen.swipes.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import com.github.pwittchen.swipes.library.Swipes;
import com.github.pwittchen.swipes.library.SwipeListener;
import pwittchen.com.swipedetector.R;

public class SwipesActivity extends AppCompatActivity {

  protected TextView info;
  private Swipes swipes;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    info = (TextView) findViewById(R.id.info);
    swipes = new Swipes();
    swipes.addListener(new SwipeListener() {
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
        info.setText("SWIPING_DOWN");
      }

      @Override public void onSwipedDown(final MotionEvent event) {
        info.setText("SWIPED_DOWN");
      }
    });
  }

  @Override public boolean dispatchTouchEvent(MotionEvent event) {
    swipes.onTouchEvent(event);
    return super.dispatchTouchEvent(event);
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
        final Intent intent = new Intent(this, SwipesRxActivity.class);
        startActivity(intent);
        break;
    }
    return true;
  }
}
