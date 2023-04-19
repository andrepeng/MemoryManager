package com.autoai.memory.manager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.autoai.memory.mylibrary.TestServiceInterface;


public class TestService extends Service {

    private final static String TAG = "TestServiceTag";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private Binder binder = new TestServiceInterface.Stub() {
        @Override
        public int func() throws RemoteException {
            return 1;
        }

        @Override
        public void onewayFunc() throws RemoteException {
            Log.d(TAG, "---onewayFunc---,thread:" + Thread.currentThread().getName());
            try {
                Thread.sleep(8*1000);
            } catch (InterruptedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            Log.d(TAG, "---sleep end---");

        }

        @Override
        public void asyncFunc() throws RemoteException {
            Log.d(TAG, "---asyncFunc---,thread:" + Thread.currentThread().getName());
            try {
                Thread.sleep(8*1000);
            } catch (InterruptedException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            Log.d(TAG, "---sleep end---");
        }
    };




}