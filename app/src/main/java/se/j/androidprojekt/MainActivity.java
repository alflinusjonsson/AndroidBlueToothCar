/*
 * Main Activity
 * Android application for Arduino and bluetooth controlled car
 * Created by Linus Jönsson, Anton Karlsson, Oscar Schenstöm
 * 2019-03-06 Version 1.0
 */

package se.j.androidprojekt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import java.io.Serializable;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MainActivity extends AppCompatActivity implements Serializable {

    RadarView mRadarView = null;
    int speedOutput;
    int distanceFront;
    int distanceBack;
    BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mRadarView = (RadarView) findViewById(R.id.radarView);

        mRadarView.setShowCircles(true);
        if (mRadarView != null) mRadarView.startAnimation();
    }

    public void SettingsButtonClicked(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void driveOnClick(View view){

            for(int i = 0; i<Data.log.size(); i++){
                Data.logList currentTask = Data.log.get(i);
                String todo = currentTask.toString();
                drive(todo);
            }

            drive("S");
    }

    public void ArrowUpPressed(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Data.log.add(new Data.logList("F"));
                switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    drive("F");
                    break;

                case MotionEvent.ACTION_UP:
                    drive("S");
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
                Data.log.add(new Data.logList("L"));
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
                Data.log.add(new Data.logList("R"));
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
                Data.log.add(new Data.logList("B"));
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
        if(distance < 15){
            distanceDot.setY(500);
            distanceDot.setBackgroundColor(Color.BLACK);
        }
        else if(distance < 30){
            distanceDot.setY(600);
            distanceDot.setBackgroundColor(Color.BLACK);
        }
        else if(distance < 50){
            distanceDot.setY(700);
            distanceDot.setBackgroundColor(Color.BLACK);
        }
        else if(distance < 100){
            distanceDot.setY(800);
            distanceDot.setBackgroundColor(Color.BLACK);
        }
        else if(distance==0 || distance > 100){
           distanceDot.setBackgroundColor(6334512);
        }

    }

    public void printDisDotFront(int distance){
        TextView distanceDot = (TextView)findViewById(R.id.disDotFront);
        if(distance < 15){
            distanceDot.setY(380);
            distanceDot.setBackgroundColor(Color.BLACK);
        }
        else if(distance < 30){
            distanceDot.setY(265);
            distanceDot.setBackgroundColor(Color.BLACK);
        }
        else if(distance < 50){
            distanceDot.setY(200);
            distanceDot.setBackgroundColor(Color.BLACK);
        }
        else if(distance < 100){
            distanceDot.setY(50);
            distanceDot.setBackgroundColor(Color.BLACK);
        }
        else if(distance == 0 || distance > 100){
            distanceDot.setBackgroundColor(6334512);
        }
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






