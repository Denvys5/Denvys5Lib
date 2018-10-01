package com.denvys5;

import com.tulskiy.keymaster.common.Provider;

import java.awt.*;

public class DenvysKeyListener {
    public static Provider provider;
    public static Robot robot;
    static {
        try {
            DenvysKeyListener.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    private static boolean KeyListenerEnabled = false;
    public static void init(){
        provider = Provider.getCurrentProvider(true);
        KeyListenerEnabled = true;
    }

    public static boolean isKeyListenerEnabled(){
        return KeyListenerEnabled;
    }
}
