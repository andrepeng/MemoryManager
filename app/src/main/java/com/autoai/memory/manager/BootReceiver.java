package com.autoai.memory.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    private final static String TAG = "BootReceiverTag";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "---onReceive---");
        Intent i = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        i.setClass(context, WindowMonitorService.class);
        context.startService(i);
    }
}