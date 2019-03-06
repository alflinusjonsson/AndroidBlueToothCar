/*
 * Settings class
 * Used for printing and handling gui of the settings page
 * 2019-03-06 Version 1.0
 */


package se.j.androidprojekt;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    public void bluetoothButtonClicked(View view){
        Intent intent = new Intent(this, BlueTooth.class);
        startActivity(intent);
    }

    public void logButtonClicked(View view){
        Intent intent = new Intent(this, LogManagement.class);
        startActivity(intent);
    }

}

