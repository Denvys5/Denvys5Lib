package com.denvys5.utils;

import com.denvys5.Config;

import java.io.*;

public class BaseUtils {
    public static ConfigUtils config = new ConfigUtils(Config.configName, getConfigName());

    public static File getConfigName() {
        if (Config.baseconf.equals("."))
            return new File(Config.baseconf + File.separator + Config.configName);
        String path = File.separator + Config.baseconf + File.separator + Config.configName;
        return new File(path);
    }

    static {
        config.load();
    }

    public static boolean isSetProperty(String key){
        return config.checkProperty(key);
    }

    public static void setProperty(String s, Object value) {
        if (config.checkProperty(s)) config.changeProperty(s, value);
        else config.put(s, value);
    }

    public static String getPropertyString(String s) {
        if (config.checkProperty(s)) return config.getPropertyString(s);
        return null;
    }

    public static boolean getPropertyBoolean(String s) {
        if (config.checkProperty(s)) return config.getPropertyBoolean(s);
        return false;
    }

    public static int getPropertyInt(String s) {
        if (config.checkProperty(s)) return config.getPropertyInteger(s);
        return 0;
    }

    public static int getPropertyInt(String s, int d) {
        if (config.checkProperty(s)) return config.getPropertyInteger(s);
        return d;
    }

    public static boolean getPropertyBoolean(String s, boolean b) {
        if (config.checkProperty(s)) return config.getPropertyBoolean(s);
        return b;
    }

    public static String getPropertyString(String s, String str) {
        if (config.checkProperty(s)) return config.getPropertyString(s);
        return str;
    }
}
