package com.denvys5;

import com.denvys5.utils.BaseUtils;

public class Config implements DefaultParameters{

    public static String baseconf;
    public static String configName;
    public static boolean debug;

    public static void initConfig(String path, String filename) {
        if (path == null || path.isEmpty())
            baseconf = System.getProperty("user.home");
        else
            baseconf = path;
        if (filename == null || filename.isEmpty())
            configName = "settings.cfg";
        else
            configName = filename;

        debug = BaseUtils.getPropertyBoolean(DEBUG_PROPERTY, DEFAULT_DEBUG);
        BaseUtils.setProperty(DEBUG_PROPERTY, debug);
    }

    public static void initConfig(String path) {
        initConfig(path, null);
    }

    public static void initConfig() {
        initConfig(null, null);
    }

}
