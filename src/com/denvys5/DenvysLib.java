package com.denvys5;

public class DenvysLib {
    public static final String domain = "sagitarium.org";
    public static final String siteDir = "minenew";
    public static final String[] p = {"wireshark", "cheat"};
    public static final String http = "http://";
    public static final String masterVersion = "1.8";
    public static String baseconf;
    public static String configName;
    public static boolean debug = true;

    public static void initConfig(String path, String filename) {
        if (path == null || path.isEmpty())
            baseconf = "Desktop";
        else
            baseconf = path;
        if (filename == null || filename.isEmpty())
            configName = "settings.cfg";
        else
            configName = filename;
    }

    public static void initConfig(String path) {
        initConfig(path, null);
    }

    public static void initConfig() {
        initConfig(null, null);
    }
}
