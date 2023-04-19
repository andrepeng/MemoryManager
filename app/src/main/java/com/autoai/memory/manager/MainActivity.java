package com.autoai.memory.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        i.setClass(this, WindowMonitorService.class);
        startService(i);
    }
}