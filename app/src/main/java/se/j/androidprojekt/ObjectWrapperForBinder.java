package se.j.androidprojekt;

import android.os.Binder;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class ObjectWrapperForBinder extends Binder {
    private final BluetoothSPP mData;

    public ObjectWrapperForBinder(BluetoothSPP data) {
        mData = data;
    }

    public BluetoothSPP getData() {
        return mData;
    }
}
