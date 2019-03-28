package com.denvys5.utils.serial;

import com.denvys5.DefaultParameters;
import com.denvys5.DenvysLib;
import jssc.SerialPortList;

import java.util.*;

public class SerialListener extends Thread {
    private boolean connected = false;
    private boolean connectedAndApproved = false;
    private int counter = 0;
    private ArrayList<String> lastPortList;
    private String currentDevice;
    @Override
    public void run(){
        while(!isInterrupted()){
            if(lastPortList==null){
                lastPortList = new ArrayList<>();
                lastPortList.addAll(Arrays.asList(SerialPortList.getPortNames()));
                if(lastPortList.size() > 0){
                    connected = onStartupCheckAllDevices(lastPortList);
                    counter = 0;
                }
                // TODO: DO SOME SHIT HERE
                continue;
            }

            if(DenvysLib.debug) System.out.println(lastPortList);
            try {
                Thread.sleep(DefaultParameters.waitForDevice/ DefaultParameters.waitForDeviceCycles);
            } catch (InterruptedException e) {
            }

            ArrayList<String> currentPortList = new ArrayList<>();
            currentPortList.addAll(Arrays.asList(SerialPortList.getPortNames()));
            if(currentPortList.size() < lastPortList.size()){
                disconnect();
            }

            if(connectedAndApproved){
                continue;
            }

            if(currentPortList.size() > lastPortList.size() && !connected){
                if(DenvysLib.debug) System.out.println(currentPortList);

                ArrayList<String> temp = new ArrayList<>(currentPortList);
                currentPortList.removeAll(lastPortList);
                lastPortList = temp;
                connect(currentPortList.get(0));
            }else{
                lastPortList = currentPortList;
            }

            if(connected && !connectedAndApproved){
                counter++;
                if(counter> DefaultParameters.waitForDeviceCycles)
                    disconnect();
            }
        }
    }

    private void connect(String com){
        if(DenvysLib.debug) System.out.println(com);
        currentDevice = com;
        DenvysLib.serialConnector = SerialConnector.getInstance(com);
        DenvysLib.serialConnector.send(DenvysLib.serialConnector.handShakeToken);
    }

    private void disconnect(){
        DenvysLib.serialConnector.disconnect();
        DenvysLib.serialConnector = null;
        SerialConnector.removeInstance();
        connected = false;
        connectedAndApproved = false;
        counter = 0;
    }

    protected void setConnected(){
        connectedAndApproved = true;
        if(DenvysLib.debug) System.out.println("Device approved" + currentDevice);
    }

    private boolean onStartupCheckAllDevices(List<String> deviceList){
        for (int i = deviceList.size()-1; i > 0; i--) {
            connect(deviceList.get(i));
            try {
                Thread.sleep(DefaultParameters.waitForDevice);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!connectedAndApproved) disconnect();
            if(connectedAndApproved) return true;
        }
        return false;
    }
}
