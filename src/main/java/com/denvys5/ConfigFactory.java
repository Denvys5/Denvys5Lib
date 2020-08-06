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


    public static Object getJsonObject(String absolutePath, Class<?> target){
        return JsonReader.getObject(absolutePath, target);
    }

    public static Object getJsonObjectWithRelativeFilePath(String relativePath, Class<?> target){
        return JsonReader.getObject(Utils.getRelativeFilepath()+relativePath, target);
    }

    public static void saveJsonObject(String absolutePath, Object object){
        JsonReader.saveObject(absolutePath, object);
    }

    public static void saveJsonObjectWithRelativeFilePath(String relativePath, Object object){
        JsonReader.saveObject(Utils.getRelativeFilepath()+relativePath, object);
    }
}
