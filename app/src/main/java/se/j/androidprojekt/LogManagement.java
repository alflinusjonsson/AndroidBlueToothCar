/*
 * LogManagement class
 * Used for log control, prints out log in application. Can be used for debugging and general information
 * 2019-03-06 Version 1.0
 */

package se.j.androidprojekt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LogManagement extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.log_management);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<Data.logList>(
                this,
                android.R.layout.simple_list_item_1,
                Data.log
        ));

    }
}