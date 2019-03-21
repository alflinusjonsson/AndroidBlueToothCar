/*
 * Settings Activity
 * Used for printing and handling gui of the settings page
 * 2019-03-06 Version 1.0
 */

package se.j.androidprojekt;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void bluetoothButtonClicked(View view){
        Intent intent = new Intent(this, BlueToothActivity.class);
        startActivity(intent);
    }

    public void logButtonClicked(View view){
        Intent intent = new Intent(this, LogManagementActivity.class);
        startActivity(intent);
    }
    public void driveOnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

