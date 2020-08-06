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


    public static <T> T getJsonObject(String absolutePath, Class<T> target){
        return JsonReader.getObject(absolutePath, target);
    }

    public static <T> T getJsonObjectWithRelativeFilePath(String relativePath, Class<T> target){
        return JsonReader.getObject(Utils.getRelativeFilepath()+relativePath, target);
    }

    public static void saveJsonObject(String absolutePath, Object object){
        JsonReader.saveObject(absolutePath, object);
    }

    public static void saveJsonObjectWithRelativeFilePath(String relativePath, Object object){
        JsonReader.saveObject(Utils.getRelativeFilepath()+relativePath, object);
    }
}
