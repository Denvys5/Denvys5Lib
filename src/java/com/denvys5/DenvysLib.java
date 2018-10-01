package com.denvys5;

import com.denvys5.utils.BaseUtils;
import com.denvys5.utils.serial.SerialConnector;
import com.denvys5.utils.serial.SerialListener;

public class DenvysLib implements DefaultParameters{

    public static String baseconf;
    public static String configName;
    public static boolean debug;
    public static int maxButtonPresses;
    public static int maxButtonPressTime;
    public static boolean allowSameHotkey;

    public static SerialListener serialListener;
    public static SerialConnector serialConnector;

    public static final String masterVersion = "1.1";


    public static void initConfig(String path, String filename) {
        if (path == null || path.isEmpty())
            baseconf = "Desktop";
        else
            baseconf = path;
        if (filename == null || filename.isEmpty())
            configName = "settings.cfg";
        else
            configName = filename;

        debug = BaseUtils.getPropertyBoolean(DEBUG_PROPERTY, DEFAULT_DEBUG);
        BaseUtils.setProperty(DEBUG_PROPERTY, debug);

        if(DenvysKeyListener.isKeyListenerEnabled()) {
            allowSameHotkey = BaseUtils.getPropertyBoolean(ALLOW_SAME_HOTKEY_PROPERTY, DEFAULT_ALLOW_SAME_HOTKEY);
            BaseUtils.setProperty(ALLOW_SAME_HOTKEY_PROPERTY, allowSameHotkey);

            maxButtonPressTime = BaseUtils.getPropertyInt(MAX_BUTTON_PRESS_TIME_PROPERTY, DEFAULT_MAX_BUTTON_PRESS_TIME);
            BaseUtils.setProperty(MAX_BUTTON_PRESS_TIME_PROPERTY, maxButtonPressTime);

            maxButtonPresses = BaseUtils.getPropertyInt(MAX_BUTTON_PRESSES_PROPERTY, DEFAULT_MAX_BUTTON_PRESSES);
            BaseUtils.setProperty(MAX_BUTTON_PRESSES_PROPERTY, maxButtonPresses);
        }
    }

    public static void initConfig(String path) {
        initConfig(path, null);
    }

    public static void initConfig() {
        initConfig(null, null);
    }

    public static void initSerial(SerialListener serialListener, SerialConnector serialConnector){
        DenvysLib.serialListener = serialListener;
        DenvysLib.serialConnector = serialConnector;
    }

}
