package com.github.maxgalkin.servicetestapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class MyTestService extends Service {
    private static String TAG = "MyTestService";
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        MyTestService getService() {
            Log.d(TAG, "getService");
            return MyTestService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    public String getCurrentDate() {
        Log.d(TAG, "getCurrentDate");
        return Calendar.getInstance().getTime().toString();
    }
}
