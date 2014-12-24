package pwittchen.com.swipedetector;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class SwipeDetectorActivity extends ActionBarActivity {

    private final static String TAG = "SwipeDetector";
    private SwipeDetector swipeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeDetector = new SwipeDetector(getSwipeListener(this));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        swipeDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private SwipeDetector.SwipeListener getSwipeListener(final Context context) {
        return new SwipeDetector.SwipeListener() {
            @Override
            public void onSwipingLeft(MotionEvent event) {
                Log.d(TAG, "onSwipingLeft");
            }

            @Override
            public void onSwipedLeft(MotionEvent event) {
                Log.d(TAG, "onSwipedLeft");
                Toast.makeText(context, "swiped left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipingRight(MotionEvent event) {
                Log.d(TAG, "onSwipingRight");
            }

            @Override
            public void onSwipedRight(MotionEvent event) {
                Log.d(TAG, "onSwipedRight");
                Toast.makeText(context, "swiped right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipingUp(MotionEvent event) {
                Log.d(TAG, "onSwipingUp");
            }

            @Override
            public void onSwipedUp(MotionEvent event) {
                Log.d(TAG, "onSwipedUp");
                Toast.makeText(context, "swiped up!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipingDown(MotionEvent event) {
                Log.d(TAG, "onSwipingDown");
            }

            @Override
            public void onSwipedDown(MotionEvent event) {
                Log.d(TAG, "onSwipedDown");
                Toast.makeText(context, "swiped down!", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
