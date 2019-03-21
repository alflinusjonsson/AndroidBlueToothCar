/*
 * BlueToothActivity class
 * Used for setting up and maintain bluetooth connection to a device. Based on the BluetoothSPP library.
 * 2019-03-06 Version 1.0
 */

package se.j.androidprojekt;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.Serializable;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class BlueToothActivity extends AppCompatActivity implements Serializable {

    BluetoothSPP bluetooth;
    Button connect;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bluetooth = new BluetoothSPP(this);
        setContentView(R.layout.bluetooth);
        connect = findViewById(R.id.connect);

        final MediaPlayer soundeffect = MediaPlayer.create(this, R.raw.bluetoothsound);

        Intent intent = getIntent();
        String direction = intent.getStringExtra("DIRECTION");

        if (!bluetooth.isBluetoothAvailable()) {

            Toast.makeText(getApplicationContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            finish();
        }

            bluetooth.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
                public void onDeviceConnected(String name, String address) {
                    connect.setText("Connected to " + name);

                    soundeffect.start();
                    final Bundle bundle = new Bundle();
                    bundle.putBinder("object_value", new ObjectWrapperForBinder(bluetooth));
                    startActivity(new Intent(BlueToothActivity.this, MainActivity.class).putExtras(bundle));

                }

                public void onDeviceDisconnected() {
                    connect.setText("Connection lost");
                }

                public void onDeviceConnectionFailed() {
                    connect.setText("Unable to connect");
                }
            });

            connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bluetooth.getServiceState() == BluetoothState.STATE_CONNECTED) {
                        bluetooth.disconnect();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                    }
                }
            });
        }

    //Checks is bluetooth is enable and starts the service
    public void onStart() {
        super.onStart();
        if (!bluetooth.isBluetoothEnabled()) {
            bluetooth.enable();
        } else {
            if (!bluetooth.isServiceAvailable()) {
                bluetooth.setupService();
                bluetooth.startService(BluetoothState.DEVICE_OTHER);
            }
        }
    }

    //Destroys bluetooth object
    public void onDestroy() {
        super.onDestroy();
        bluetooth.stopService();
    }

    //Connecting to device if available else prints out error message to user
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bluetooth.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bluetooth.setupService();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void driveForward(){
        bluetooth.send("1", true);
    }
}
