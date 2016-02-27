package com.mx3.knyghtryder;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by David on 2/26/2016.
 */
public class SdlServiceConnection implements ServiceConnection
{
    private final String LOG_TAG = "SdlServiceConnection";
    private VehicleInfoDisplay displayer;

    public SdlServiceConnection(VehicleInfoDisplay displayer)
    {
        this.displayer = displayer;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        Log.i(LOG_TAG, "onServiceConnected");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i(LOG_TAG, "onServiceDisconnected");
    }
}
