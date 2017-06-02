package com.example.ativ.tracingshaking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;

public class ScannerTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_test);

        new IntentIntegrator(this).initiateScan();
    }
}
