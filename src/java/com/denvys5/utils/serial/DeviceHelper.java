package com.denvys5.utils.serial;

public interface DeviceHelper {
    void connect();
    void disconnect();
    void send(int targetId, byte functionID, Object... value);
    void send(byte[]data);
    void onDataReceived(Object data);
    boolean isTransactionSuccessful();
}
