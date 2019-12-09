package com.github.maxgalkin.servicetestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    MyTestService testService;
    private boolean isBound = false;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        Intent intent = new Intent(this, MyTestService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        if (isBound) {
            Log.d(TAG, "onStop: unbindService");
            unbindService(connection);
            isBound = false;
        }
    }

    public void displayData(View v) {
        Log.d(TAG, "displayData");
        if (isBound) {
            Log.d(TAG, "displayData: getCurrentDate");
            String str = testService.getCurrentDate();
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            textView.setText("Last update: " + str);
            Log.d(TAG, "displayData: getCurrentDate: " + str);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            MyTestService.LocalBinder binder = (MyTestService.LocalBinder) iBinder;
            testService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
            isBound = false;
        }
    };
}
