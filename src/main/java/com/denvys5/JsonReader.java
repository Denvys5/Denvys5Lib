package com.denvys5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonReader {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T getObject(String filepath, Class<T> target){
        return gson.fromJson(readFile(filepath), target);
    }


    public static void saveObject(String absolutePath, Object target){
        InputStream input = new ByteArrayInputStream(gson.toJson(target).getBytes(StandardCharsets.UTF_8));
        File out = new File(absolutePath);
        Utils.writeToFile(input, out);
    }

    public static String readFile(String filePath){
        StringBuilder contentBuilder = new StringBuilder();

        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader;
        if (in != null) {
            reader = new BufferedReader(new InputStreamReader(in));
            reader.lines().forEach(s -> contentBuilder.append(s).append("\n"));
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.err.println("Json file not found!");
        }

        return contentBuilder.toString();
    }
}
