package com.example.a2021printsampleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Map<String, String>> mDataMapList;
    private SimpleAdapter mAdapter;
    private static final String DEVICE_NAME_KEY = "DEVICE_NAME_KEY";
    private static final String IDENTIFIER_KEY = "IDENTIFIER_KEY";
    private static final String INTERFACE_TYPE_KEY = "INTERFACE_TYPE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0x00);
        }

        ListView discoveredListView = findViewById(R.id.DiscoveredListView);

        mDataMapList = new ArrayList<>();

        mAdapter = new SimpleAdapter(this, mDataMapList, R.layout.list_discovered_row, new String[]{INTERFACE_TYPE_KEY, DEVICE_NAME_KEY, IDENTIFIER_KEY},
                new int[]{R.id.InterfaceType, R.id.DeviceName, R.id.IdentifierText});
        discoveredListView.setAdapter(mAdapter);

        discoveredListView.setOnItemClickListener((adapterView, view, i, l) -> {
            mDataMapList.clear();
            mAdapter.notifyDataSetChanged();

            TextView identifierTextView = view.findViewById(R.id.IdentifierText);
            String identifier = identifierTextView.getText().toString();

            /*TextView interfaceType = view.findViewById(R.id.InterfaceType); // will be needed in the future when other interfaces are introduced
            String interfaceConnection = interfaceType.getText().toString();*/

            TextView deviceName= view.findViewById(R.id.DeviceName);
            String nameOfDevice = deviceName.getText().toString();

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra(SecondActivity.IDENTIFIER_BUNDLE_KEY, identifier);
            //intent.putExtra(SecondActivity.INTERFACE_BUNDLE_KEY, interfaceConnection); future use for new interfaces
            intent.putExtra(SecondActivity.DEVICE_NAME_BUNDLE_KEY, nameOfDevice);

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{List<PortInfo> portInfos = StarIOPort.searchPrinter("BT:", this);
            for (int i=0; i<portInfos.size(); i++){
                Map<String, String> map = new HashMap<>();
                map.put(INTERFACE_TYPE_KEY, portInfos.get(i).getPortName());
                map.put(IDENTIFIER_KEY, portInfos.get(i).getMacAddress());
                map.put(DEVICE_NAME_KEY, portInfos.get(i).getModelName());
                //map.put(USB_SERIAL_NUMBER, portInfos.get(i).getUSBSerialNumber());  future use USB connection

                mDataMapList.add(map);
            }
            mAdapter.notifyDataSetChanged();
        } catch (StarIOPortException e) {
            e.printStackTrace();
        }
    }
}