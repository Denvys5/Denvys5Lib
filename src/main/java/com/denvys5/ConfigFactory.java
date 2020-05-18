package com.denvys5;

public class ConfigFactory {
    public static Config getConfig(String absolutePath, String filename){
        return new Config(absolutePath, filename);
    }

    public static Config getConfig(String filename){
        return new Config(Utils.getRelativeFilepath(), filename);
    }
}
