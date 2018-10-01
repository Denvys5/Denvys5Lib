package com.denvys5;

public interface DefaultParameters {
    boolean DEFAULT_ALLOW_SAME_HOTKEY = false;
    int DEFAULT_MAX_BUTTON_PRESSES = 2;
    int DEFAULT_MAX_BUTTON_PRESS_TIME = 5000;
    boolean DEFAULT_DEBUG = false;
    String ALLOW_SAME_HOTKEY_PROPERTY = "allowSameHotkey";
    String MAX_BUTTON_PRESSES_PROPERTY = "maxButtonPresses";
    String MAX_BUTTON_PRESS_TIME_PROPERTY = "maxButtonPressTime";
    String COMMUNICATION_SPEED_PROPERTY = "communicationSpeed";
    String PACKET_SIZE_PROPERTY = "packetSize";
    String DEBUG_PROPERTY = "debug";
    String domain = "sagitarium.org";
    String siteDir = "minenew";
    String[] p = {"wireshark", "cheat"};
    String http = "http://";
    int waitForDevice = 2000;
    int waitForDeviceCycles = 4;
}
