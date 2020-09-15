/*
 *  Copyright (C) 2020  Denys Vysoven
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.denvys5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonReader {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T getObject(String filepath, Class<T> target) throws FileNotFoundException, JsonSyntaxException, JsonIOException {
        return gson.fromJson(readFile(filepath), target);
    }


    public static void saveObject(String absolutePath, Object target){
        InputStream input = new ByteArrayInputStream(gson.toJson(target).getBytes(StandardCharsets.UTF_8));
        File out = new File(absolutePath);
        Utils.writeToFile(input, out);
    }

    public static String readFile(String filePath) throws FileNotFoundException {
        StringBuilder contentBuilder = new StringBuilder();

        InputStream in = new FileInputStream(filePath);
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(in));
        reader.lines().forEach(s -> contentBuilder.append(s).append("\n"));
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}
