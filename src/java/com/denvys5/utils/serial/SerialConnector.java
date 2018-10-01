package com.denvys5.utils.serial;

import com.denvys5.DenvysLib;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class SerialConnector implements DeviceHelper {
    public int communicationSpeed = SerialPort.BAUDRATE_9600;
    public int callbackTimeout = 500;
    public int packetSize = 8;
    public byte[] handShakeToken = {1,1,1};
    private SerialPort deviceConnection;
    private boolean currentTransactionState = false;
    private String device;
    private static SerialConnector instance = null;
    public static SerialConnector getInstance(String com){
        if(instance == null){
            instance = new SerialConnector(com);
            instance.connect();
            instance.device = com;
        }
        return instance;
    }
    public static void removeInstance(){
        instance = null;
    }
    private SerialConnector(String deviceConnection) {
        this.deviceConnection = new SerialPort(deviceConnection);
    }

    @Override
    public void connect() {
        if(!deviceConnection.isOpened()){
            if(DenvysLib.debug) System.out.println("connected " + device);
            try {
                deviceConnection.openPort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                deviceConnection.setParams(communicationSpeed,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
            try {
                deviceConnection.addEventListener(new SerialReader());
            } catch (SerialPortException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void disconnect() {
        if(deviceConnection.isOpened()){
            try {
                deviceConnection.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public  void  send(int targetID, byte functionID, Object... values) {
        currentTransactionState = false;
        if (deviceConnection.isOpened()){
            int iterator = 0;
            byte[] result = new byte[packetSize];
            result[0] = functionID;
            result[1] = (byte)targetID;
            iterator = 2;
            if (values!=null) {
                for (int i = 0; i < values.length; i++) {
                    if(values[i] instanceof Integer) {
                        byte[] buffer = ByteBuffer.allocate(4).putInt((Integer) values[i]).array();
                        for (int j = 3; j >=0; j--) {
                            result[iterator] = buffer[j];
                            iterator++;
                        }

                    }else if (values[i]instanceof Byte) {
                        result[iterator] = (Byte) values[i];
                        iterator++;
                    }else if(values[i] instanceof byte[]){
                        for(byte value : (byte[])values[i]){
                            result[iterator] = value;
                            iterator++;
                        }
                    }
                }
            }
            if(DenvysLib.debug) System.out.println("We send into " + device + " message " + Arrays.toString(result));
            try {
                deviceConnection.writeBytes(result);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void send(byte[] data) {
        currentTransactionState = false;
        if (deviceConnection.isOpened()){
            if(DenvysLib.debug) System.out.println("We send into " + device + " message " + Arrays.toString(data));
            try {
                deviceConnection.writeBytes(data);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    private void waitForRequestApproval() {
        long start = System.currentTimeMillis();
        while (!isTransactionSuccessful()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis()-start>=callbackTimeout){
                currentTransactionState = false;
                break;
            }

        }
    }

    @Override
    public void onDataReceived(Object data) {
        if(DenvysLib.debug) System.out.println("We got a message " + Arrays.toString((byte[])data));
        if (data instanceof byte[] || data instanceof Byte[]){
            if(handShake((byte[])data)) {
                DenvysLib.serialListener.setConnected();
            }
            actionOnDataReceived((byte[]) data);
        }
    }

    private boolean handShake(byte[] data){
        for(int i = 0; i < handShakeToken.length; i++){
            if(data[i] != handShakeToken[i]) return false;
        }
        return true;
    }

    private void actionOnDataReceived(byte[] data){}

    @Override
    public boolean isTransactionSuccessful() {
        return currentTransactionState;
    }

    private class SerialReader implements SerialPortEventListener {
        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0){
                byte[] packet = null;
                try {
                    packet = deviceConnection.readBytes();
                    onDataReceived(packet);
                }catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
