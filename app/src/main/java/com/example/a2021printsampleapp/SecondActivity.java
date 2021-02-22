package com.example.a2021printsampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.starmicronics.stario.StarIOPort;

public class SecondActivity extends AppCompatActivity {

    public static final String IDENTIFIER_BUNDLE_KEY = "IDENTIFIER_BUNDLE_KEY";
    public static final String INTERFACE_BUNDLE_KEY = "INTERFACE_BUNDLE_KEY";
    public static final String DEVICE_NAME_BUNDLE_KEY = "DEVICE_NAME_BUNDLE_KEY";
    public static final String USB_SERIAL_KEY = "USB_SERIAL_KEY";

    private StarIOPort mPort;
    private byte[] mCommands ;
    private String macAddress = IDENTIFIER_BUNDLE_KEY;
    private String mPortType;
    private String mPortIdentifier;
    private TextView mPrinterName;
    private TextView mMelody;
    private TextView mScanner;
    private TextView mScreenDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mPortType = getIntent().getStringExtra(INTERFACE_BUNDLE_KEY);
        mPortIdentifier = getIntent().getStringExtra(IDENTIFIER_BUNDLE_KEY);
        mPrinterName = findViewById(R.id.printerName);

        mPrinterName.setText(mPortType);
    }

    public void printTest(View view) {
        
    }

    public void cashDrawer(View view) {
    }
}