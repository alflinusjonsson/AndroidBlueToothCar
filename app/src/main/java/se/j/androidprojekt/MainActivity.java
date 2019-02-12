package se.j.androidprojekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    RadarView mRadarView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mRadarView = (RadarView) findViewById(R.id.radarView);
        mRadarView.setShowCircles(true);
        if (mRadarView != null) mRadarView.startAnimation();
    }

    public void SettingsButtonClicked(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void ArrowUpPressed(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //Run motor forward
                        break;
                    case MotionEvent.ACTION_UP:
                        //Stop motor
                        break;
                }
                return false;
            }
        });
    }
    public void ArrowLeftPressed(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //Run motor left
                        break;
                    case MotionEvent.ACTION_UP:
                        //Stop motor
                        break;
                }
                return false;
            }
        });
    }

    public void ArrowRightPressed(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //Run motor right
                        break;
                    case MotionEvent.ACTION_UP:
                        //Stop motor
                        break;
                }
                return false;
            }
        });
    }

    public void ArrowDownPressed(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //Run motor backwards
                        break;
                    case MotionEvent.ACTION_UP:
                        //Stop motor
                        break;
                }
                return false;
            }
        });
    }

}






