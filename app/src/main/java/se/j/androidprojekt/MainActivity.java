/*
 * Android application for Arduino and bluetooth controlled car
 * Created by Linus Jönsson, Anton Karlsson, Oscar Schenstöm
 * 2019-03-06 Version 1.0
 */

package se.j.androidprojekt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import java.io.Serializable;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MainActivity extends AppCompatActivity implements Serializable {

    RadarView mRadarView = null;
    int speedOutput = 0;
    int distanceFront = 0;
    int distanceBack = 0;
    BluetoothSPP bt;

    //final MediaPlayer closeObjectSound = MediaPlayer.create(this, R.raw.beeping);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /*TextView speedTextView = (TextView) findViewById(R.id.speed);
        speedTextView.setText(speedOutput + "\n" + "km/h");

        TextView disFront = (TextView) findViewById(R.id.disFront);
        disFront.setText(distanceFront + "\n" + "cm");

        TextView disBack = (TextView) findViewById(R.id.disBack);
        disBack.setText(distanceBack + "\n" + "cm");
        */

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
                switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    drive("F");
                    break;

                case MotionEvent.ACTION_UP:
                    drive("S");
                    System.out.println("/n 'S'");
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
                        drive("L");
                        break;
                    case MotionEvent.ACTION_UP:
                        drive("S");
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
                        drive("R");
                        break;
                    case MotionEvent.ACTION_UP:
                        drive("S");
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
                        drive("B");
                        break;
                    case MotionEvent.ACTION_UP:
                        drive("S");
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

    public void drive(String direction){
        bt = ((ObjectWrapperForBinder) getIntent().getExtras().getBinder("object_value")).getData();
        bt.send(direction, true);
        receiveData(direction);
    }

    public void receiveData(final String direction){
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                String mes = message;
                String[] cut = mes.split(",");
                System.out.println(cut[0]);
                int[] intArray = new int[cut.length];
                for(int i = 0; i<cut.length; i++) {
                    String numberAsString = cut[i];
                    intArray[i] = Integer.parseInt(numberAsString);
                }

                distanceBack = intArray[0];
                distanceFront = intArray[1];

                TextView disBack = (TextView) findViewById(R.id.disBack);
                disBack.setText(distanceBack + "\n" + "cm");
                printDisDotBack(distanceBack);

                TextView disFront = (TextView) findViewById(R.id.disFront);
                disFront.setText(distanceFront + "\n" + "cm");
                printDisDotFront(distanceFront);


                if(direction == "S")
                    speedOutput = 0;
                else
                    speedOutput = 6;
                TextView speedTextView = (TextView) findViewById(R.id.speed);
                speedTextView.setText(speedOutput + "\n" + "km/h");


            }
        });
    }

}






