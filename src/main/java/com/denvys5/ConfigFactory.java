package com.denvys5;

public class ConfigFactory implements DefaultParameters{
    public static Config getConfig(String absolutePath, String filename){
        return new Config(absolutePath, filename);
    }

    public static Config getConfig(String filename){
        return getConfig(Utils.getRelativeFilepath(), filename);
    }

    public static Config getConfig(){
        return getConfig(Utils.getRelativeFilepath(), DEFAULT_FILENAME);
    }

    public static Config getConfigWithRelativeFilePath(String relativePath, String filename){
        return getConfig(Utils.getRelativeFilepath() + relativePath, filename);
    }
}
