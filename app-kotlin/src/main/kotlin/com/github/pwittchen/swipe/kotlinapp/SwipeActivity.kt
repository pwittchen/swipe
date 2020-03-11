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
package com.github.pwittchen.swipe.kotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.github.pwittchen.swipe.library.rx2.Swipe
import com.github.pwittchen.swipe.library.rx2.SwipeListener
import kotlinx.android.synthetic.main.activity_main.info

class SwipeActivity : AppCompatActivity() {

  private var swipe: Swipe? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    swipe = Swipe()
    (swipe as Swipe).setListener(object : SwipeListener {
      override fun onSwipingLeft(event: MotionEvent) {
        info.text = "SWIPING_LEFT"
      }

      override fun onSwipedLeft(event: MotionEvent): Boolean {
        info.text = "SWIPED_LEFT"
        return false
      }

      override fun onSwipingRight(event: MotionEvent) {
        info.text = "SWIPING_RIGHT"
      }

      override fun onSwipedRight(event: MotionEvent): Boolean {
        info.text = "SWIPED_RIGHT"
        return false
      }

      override fun onSwipingUp(event: MotionEvent) {
        info.text = "SWIPING_UP"
      }

      override fun onSwipedUp(event: MotionEvent): Boolean {
        info.text = "SWIPED_UP"
        return false
      }

      override fun onSwipingDown(event: MotionEvent) {
        info.text = "SWIPING_DOWN"
      }

      override fun onSwipedDown(event: MotionEvent): Boolean {
        info.text = "SWIPED_DOWN"
        return false
      }
    })
  }

  override fun dispatchTouchEvent(event: MotionEvent): Boolean {
    (swipe as Swipe).dispatchTouchEvent(event)
    return super.dispatchTouchEvent(event)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.listener -> {
      }
      R.id.rx -> {
        val intent = Intent(this, SwipeRxActivity::class.java)
        startActivity(intent)
      }
    }
    return true
  }
}
