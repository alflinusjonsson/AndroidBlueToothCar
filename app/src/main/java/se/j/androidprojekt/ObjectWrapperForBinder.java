/*
 * ObjectWrapperForBinder class
 * Used for sending object between two classes
 * 2019-03-06 Version 1.0
 */

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
