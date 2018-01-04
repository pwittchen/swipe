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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pwittchen.com.swipedetector.R;

public class SwipeRxActivity extends AppCompatActivity {
  protected TextView info;
  private Swipe swipe;
  private Disposable disposable;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    info = (TextView) findViewById(R.id.info);
    swipe = new Swipe();
    disposable = swipe.observe()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(swipeEvent -> info.setText(swipeEvent.toString()));
  }

  @Override public boolean dispatchTouchEvent(MotionEvent event) {
    swipe.dispatchTouchEvent(event);
    return super.dispatchTouchEvent(event);
  }

  @Override protected void onPause() {
    super.onPause();
    safelyUnsubscribe(disposable);
  }

  private void safelyUnsubscribe(Disposable disposable) {
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.listener:
        onBackPressed();
        break;
      case R.id.rx:
        break;
    }
    return true;
  }
}
