package com.autoai.memory.manager;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class WindowMonitorService extends AccessibilityService {
    private final static String TAG = "WindowMonitorService";

    private final static int TIME_GAP = 5000;

    private Handler mHandler;

    private String currentPackage;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "---onCreate--5" +
                "-");
        mHandler = new Handler();
        mHandler.postDelayed(runnable, TIME_GAP);

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent, event:" + accessibilityEvent.getEventType());
        if( accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ){
            String className = accessibilityEvent.getClassName().toString();
            String packageName = accessibilityEvent.getPackageName().toString();
            if (TextUtils.equals(packageName, currentPackage)) {
                return;
            }
            currentPackage = packageName;
            if (Constants.TOP_APP_SET.contains(currentPackage)) {
                killApps();
            }

            //监听当前窗口变化，获取Package名和Class名
            Log.i(TAG,"TYPE_WINDOW_STATE_CHANGED,className =" + className );
            Log.i(TAG,"TYPE_WINDOW_STATE_CHANGED,packageName=" + currentPackage );
        } else if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOWS_CHANGED) {
            String className = accessibilityEvent.getClassName().toString();
            String packageName = accessibilityEvent.getPackageName().toString();
            Log.i(TAG,"TYPE_WINDOWS_CHANGED,className =" + className );
            Log.i(TAG,"TYPE_WINDOWS_CHANGED,packageName=" + packageName );
        } else if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            String className = accessibilityEvent.getClassName().toString();
            String packageName = accessibilityEvent.getPackageName().toString();
            Log.i(TAG,"TYPE_WINDOW_CONTENT_CHANGED,className =" + className );
            Log.i(TAG,"TYPE_WINDOW_CONTENT_CHANGED,packageName=" + packageName );
        }

    }

    @Override
    public void onInterrupt() {

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //openAccessibility();
            mHandler.postDelayed(this, TIME_GAP);
        }
    };

    private void openAccessibility() {
        //Log.d(TAG, "---openAccessibility--2-");
        Settings.Secure.putString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES ,"com.autoai.memory.manager/com.autoai.memory.manager.WindowMonitorService");
        Settings.Secure.putInt(getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED, 1);
    }

    private void killApps() {
        ActivityManager mgr = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int size = BlackList.APP_KILL_LIST.length;
        Log.d(TAG, "---killApps---, availMem:" + getFreeMem(this));

        /*for (int i = 0; i < size; i++) {
            mgr.forceStopPackage(BlackList.APP_KILL_LIST[i]);
        }*/
        Log.d(TAG, "---killApps-finish--, availMem:" + getFreeMem(this));

    }

    public static long getFreeMem(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        // 单位Byte
        return info.availMem;
    }



}