package com.example.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.example.ResponseStatus;
import com.example.example.USBSerialConnector;
import com.example.example.USBSerialListener;
import com.example.example.Utilities;
import com.example.example.UsbSerialInterface;
/*
import com.felhr.usbserial.UsbSerialDevice;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
*/
public class MainActivity extends AppCompatActivity implements USBSerialListener {

    USBSerialConnector mConnector;
    EditText txTEXT;
    TextView rxTEXT;
    Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnector = USBSerialConnector.getInstance();
        txTEXT = (EditText) findViewById(R.id.txtText);
        rxTEXT = (TextView) findViewById(R.id.rxText);
        btSend = (Button) findViewById(R.id.btSend);

        rxTEXT.setMovementMethod(new ScrollingMovementMethod());

        txTEXT.setEnabled(false);
        rxTEXT.setEnabled(false);
        btSend.setEnabled(false);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = txTEXT.getText().toString();
                mConnector.writeAsync(data.getBytes());
            }
        });
    }

    @Override
    public void onDataReceived(byte[] data) {
        if (data != null) {
            //rxTEXT.setText(rxTEXT.getText()+"\n HEX>"+Utilities.bytesToString(data));
            //rxTEXT.setText(rxTEXT.getText()+"\n HEX>"+Utilities.bytesToString(data)+"\nSTRING>"+data);
            rxTEXT.append("Mensaje: "+Utilities.bytesToString(data)+"\n");
        } else {

        }
    }



    @Override
    public void onErrorReceived(String data) {
        //Toast.makeText(this, data, Toast.LENGTH_LONG).show();
       Toast.makeText(this,"Un error ha occurrido, pruebe reiniciando la app con el dispositivo conectado",Toast.LENGTH_LONG);
    }

    @Override
    public void onDeviceReady(ResponseStatus responseStatus) {
        txTEXT.setEnabled(true);
        rxTEXT.setEnabled(true);
        btSend.setEnabled(true);
    }

    @Override
    public void onDeviceDisconnected() {
        Toast.makeText(this, "Device disconnected", Toast.LENGTH_LONG).show();
        //finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mConnector.setUsbSerialListener(this);
        mConnector.init(this, 115200);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mConnector.pausedActivity();
    }
}