package se.j.androidprojekt;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class BlueToothConnectionSerivce {

    private static final String tag = "BlueToothConnectionServ";
    private static final String appname = "RC-Car";
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;

    private ConnectThread mConnectThread;
    private BluetoothDevice mDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;

    private ConnectedThread mConnectedThread;

    public BlueToothConnectionSerivce(Context context){
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private class AcceptThread extends Thread {

        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;

            try{
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appname, MY_UUID_INSECURE);

                Log.d(tag, "AcceptThread: Setting up server using:" + MY_UUID_INSECURE);
            }catch (IOException e){
                Log.d(tag, "AcceptThread: IOExeption" + e.getMessage() );
            }

            mmServerSocket = tmp;

        }

        public void run() {
            Log.d(tag,"run: AcceptThread Running");

            BluetoothSocket socket = null;

            try{
                Log.d(tag, "run: RFCOM server socket start...");

                socket = mmServerSocket.accept();

                Log.d(tag, "run: RFCOM server accepted connection.");
            }catch (IOException e){
                Log.d(tag, "AcceptThread: IOExeption" + e.getMessage() );
            }

            if(socket != null){
                connected(socket, mDevice);
            }

            Log.d(tag, "END AcceptThread");
        }

        public void cancel() {
            Log.d(tag,"cancel: Canceling AcceptThread");
            try{
                mmServerSocket.close();
            }catch (IOException e){
                Log.d(tag,"cancel: Close of AcceptThread Serversocket failed" + e.getMessage() );
            }
        }

    }

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(tag, "ConnectThread: started.");
            mDevice = device;
            deviceUUID = uuid;
        }

        public void run() {
            BluetoothSocket tmp = null;
            Log.i(tag, "RUN mConnectThread ");

            try {
                Log.d(tag, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: " +MY_UUID_INSECURE );
                tmp = mDevice.createRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                Log.e(tag, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
            }

            mmSocket = tmp;
            mBluetoothAdapter.cancelDiscovery();

            try {
                mmSocket.connect();

                Log.d(tag, "run: ConnectThread connected.");
            } catch (IOException e) {
                try {
                    mmSocket.close();
                    Log.d(tag, "run: Closed Socket.");
                } catch (IOException e1) {
                    Log.e(tag, "mConnectThread: run: Unable to close connection in socket " + e1.getMessage() );
                }
                Log.d(tag, "run: ConnectThread: Could not connect to UUID: " + MY_UUID_INSECURE );
            }

            connected(mmSocket,mDevice);
        }
        public void cancel() {
            try {
                Log.d(tag, "cancel: Closing Client Socket.");
                mmSocket.close();
            } catch (IOException e) {
                Log.e(tag, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage() );
            }
        }
    }

    public synchronized void start() {
        Log.d(tag, "start");

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    public void startClient(BluetoothDevice device,UUID uuid){
        Log.d(tag, "startClient: Started.");

        mProgressDialog = ProgressDialog.show(mContext,"Connecting Bluetooth","Please Wait...",true);

        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }


    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(tag, "ConnectedThread: Starting.");

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try{
                mProgressDialog.dismiss();
            }catch (NullPointerException e){
                e.printStackTrace();
            }


            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[1024];

            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.d(tag, "InputStream: " + incomingMessage);
                } catch (IOException e) {
                    Log.e(tag, "write: Error reading Input Stream. " + e.getMessage() );
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(tag, "write: Writing to outputstream: " + text);
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(tag, "write: Error writing to output stream. " + e.getMessage() );
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.d(tag, "connected: Starting.");

        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    public void write(byte[] out) {

        ConnectedThread r;

        Log.d(tag, "write: Write Called.");

        mConnectedThread.write(out);
    }

}
