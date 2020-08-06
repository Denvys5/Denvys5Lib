package com.denvys5;

import java.io.File;

public class Config implements DefaultParameters{
    private boolean debug;
    private final String fileName;
    private final String filePath;
    private final ConfigUtils config;

    Config(String filePath, String fileName) {
        this.fileName = fileName;
        this.filePath = filePath;

        config = new ConfigUtils(fileName, Utils.getFile(filePath, fileName));
        config.load();

        debug = addPropertyBoolean(DEBUG_PROPERTY, DEFAULT_DEBUG);
    }

    public boolean isDebug() {
        return debug;
    }

    public String addPropertyString(final String propertyName, final String defaultValue){
        if(!config.checkProperty(propertyName)) {
            config.put(propertyName, defaultValue);
            return defaultValue;
        }

        return config.getPropertyString(propertyName);
    }

    public boolean addPropertyBoolean(final String propertyName, final boolean defaultValue){
        if(!config.checkProperty(propertyName)) {
            config.put(propertyName, defaultValue);
            return defaultValue;
        }

        return config.getPropertyBoolean(propertyName);
    }

    public int addPropertyInt(final String propertyName, final int defaultValue){
        if(!config.checkProperty(propertyName)) {
            config.put(propertyName, defaultValue);
            return defaultValue;
        }

        return config.getPropertyInteger(propertyName);
    }

    public double addPropertyDouble(final String propertyName, final double defaultValue){
        if(!config.checkProperty(propertyName)) {
            config.put(propertyName, defaultValue);
            return defaultValue;
        }

        return config.getPropertyDouble(propertyName);
    }

    public boolean isSetProperty(String key){
        return config.checkProperty(key);
    }

    public void setProperty(String s, Object value) {
        if (config.checkProperty(s)) config.changeProperty(s, value);
        else config.put(s, value);
    }

    public int getPropertyInt(String s) {
        if (config.checkProperty(s)) return config.getPropertyInteger(s);
        return 0;
    }

    public int getPropertyInt(String s, int d) {
        if (config.checkProperty(s)) return config.getPropertyInteger(s);
        return d;
    }

    public boolean getPropertyBoolean(String s) {
        if (config.checkProperty(s)) return config.getPropertyBoolean(s);
        return false;
    }

    public boolean getPropertyBoolean(String s, boolean b) {
        if (config.checkProperty(s)) return config.getPropertyBoolean(s);
        return b;
    }

    public String getPropertyString(String s) {
        if (config.checkProperty(s)) return config.getPropertyString(s);
        return null;
    }

    public String getPropertyString(String s, String str) {
        if (config.checkProperty(s)) return config.getPropertyString(s);
        return str;
    }

    public double getPropertyDouble(String s){
        if (config.checkProperty(s)) return config.getPropertyDouble(s);
        return 0D;
    }

    public double getPropertyDouble(String s, double d) {
        if (config.checkProperty(s)) return config.getPropertyDouble(s);
        return d;
    }
}
