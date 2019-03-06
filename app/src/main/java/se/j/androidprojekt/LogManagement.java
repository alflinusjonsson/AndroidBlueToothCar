/*
 * LogManagement class
 * Used for log control, prints out log in application. Can be used for debugging and general information
 * 2019-03-06 Version 1.0
 */

package se.j.androidprojekt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LogManagement extends AppCompatActivity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.log_management);

            try {
                Process process = Runtime.getRuntime().exec("logcat -d");
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                StringBuilder log=new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    log.append(line);
                }
                TextView tv = (TextView)findViewById(R.id.textView);
                tv.setText(log.toString());
            } catch (IOException e) {
                // Handle Exception
            }
        }

}



