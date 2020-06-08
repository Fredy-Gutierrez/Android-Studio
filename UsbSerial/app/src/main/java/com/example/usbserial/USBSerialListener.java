package com.example.usbserial;

public interface USBSerialListener {
    void onDataReceived(byte[] data);

    void onErrorReceived(String data);

    void onDeviceReady(ResponseStatus responseStatus);

    void onDeviceDisconnected();
}