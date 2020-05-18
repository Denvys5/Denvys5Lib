package com.denvys5;

import java.io.File;

class Config implements DefaultParameters{
    private boolean debug;
    private final String fileName;
    private final String filePath;
    private final ConfigUtils config;

    public Config(String filePath, String fileName) {
        this.fileName = fileName;
        this.filePath = filePath;

        config = new ConfigUtils(fileName, getConfigName());
        config.load();

        if(!isSetProperty(DEBUG_PROPERTY)) setProperty(DEBUG_PROPERTY, DEFAULT_DEBUG);
        debug = getPropertyBoolean(DEBUG_PROPERTY);
    }

    public boolean isDebug() {
        return debug;
    }

    public File getConfigName() {
        if (filePath.equals("."))
            return new File(filePath + File.separator + fileName);
        String path = File.separator + filePath + File.separator + fileName;
        return new File(path);
    }

    public boolean isSetProperty(String key){
        return config.checkProperty(key);
    }

    public void setProperty(String s, Object value) {
        if (config.checkProperty(s)) config.changeProperty(s, value);
        else config.put(s, value);
    }

    public String getPropertyString(String s) {
        if (config.checkProperty(s)) return config.getPropertyString(s);
        return null;
    }

    public boolean getPropertyBoolean(String s) {
        if (config.checkProperty(s)) return config.getPropertyBoolean(s);
        return false;
    }

    public int getPropertyInt(String s) {
        if (config.checkProperty(s)) return config.getPropertyInteger(s);
        return 0;
    }

    public int getPropertyInt(String s, int d) {
        if (config.checkProperty(s)) return config.getPropertyInteger(s);
        return d;
    }

    public boolean getPropertyBoolean(String s, boolean b) {
        if (config.checkProperty(s)) return config.getPropertyBoolean(s);
        return b;
    }

    public String getPropertyString(String s, String str) {
        if (config.checkProperty(s)) return config.getPropertyString(s);
        return str;
    }
}
