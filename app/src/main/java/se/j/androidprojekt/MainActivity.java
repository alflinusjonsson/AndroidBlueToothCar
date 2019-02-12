package se.j.androidprojekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    RadarView mRadarView = null;
    int speedOutput = 50;
    int distanceFront = 10;
    int distanceBack = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        TextView speedTextView = (TextView) findViewById(R.id.speed);
        speedTextView.setText(speedOutput + "\n" + "km/h");

        TextView disFront = (TextView) findViewById(R.id.disFront);
        disFront.setText(distanceFront + "\n" + "cm");

        TextView disBack = (TextView) findViewById(R.id.disBack);
        disBack.setText(distanceBack + "\n" + "cm");

        mRadarView = (RadarView) findViewById(R.id.radarView);

        mRadarView.setShowCircles(true);
        if (mRadarView != null) mRadarView.startAnimation();
    }

    public void SettingsButtonClicked(View view){
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






