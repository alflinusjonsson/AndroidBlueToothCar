package se.j.androidprojekt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MainActivity extends AppCompatActivity {
    RadarView mRadarView = null;
    int speedOutput = 50;
    int distanceFront = 20;
    int distanceBack = 20;

    final String stop = "0";
    final String forward = "1";
    final String left = "3";
    final String right = "4";
    final String back = "2";
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

        printDisDotBack(distanceBack);
        printDisDotFront(distanceFront);

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
                        //bt.bluetooth.send(forward,true);
                        break;
                    case MotionEvent.ACTION_UP:
                        //bt.bluetooth.send(stop,true);
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
                        //bt.bluetooth.send(left,true);
                        break;
                    case MotionEvent.ACTION_UP:
                        //bt.bluetooth.send(stop,true);
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
                        //bt.bluetooth.send(right,true);
                        break;
                    case MotionEvent.ACTION_UP:
                        //bt.bluetooth.send(stop,true);
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
                        //bt.bluetooth.send(back,true);
                        break;
                    case MotionEvent.ACTION_UP:
                        //bt.bluetooth.send(stop,true);
                        break;
                }
                return false;
            }
        });
    }

    public void printDisDotBack(int distance){
        TextView distanceDot = (TextView)findViewById(R.id.disDotBack);
        if(distance < 5)
            distanceDot.setY(-610);
        else if(distance < 10)
            distanceDot.setY(-540);
        else if(distance < 14)
            distanceDot.setY(-460);
        else if(distance < 17)
            distanceDot.setY(-380);
        else
            distanceDot.setBackgroundColor(6334512);

    }

    public void printDisDotFront(int distance){
        TextView distanceDot = (TextView)findViewById(R.id.disDotFront);
        if(distance < 5)
            distanceDot.setY(-680);
        else if(distance < 10)
            distanceDot.setY(-750);
        else if(distance < 14)
            distanceDot.setY(-830);
        else if(distance < 17)
            distanceDot.setY(-910);
        else
            distanceDot.setBackgroundColor(6334512);
    }
}






