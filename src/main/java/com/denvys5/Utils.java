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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

class Utils {
    public static String getRelativeFilepath(){
        Optional<URL> optionalPath = Optional.ofNullable(Utils.class.getResource("."));
        if(optionalPath.isPresent()) return optionalPath.get().getPath();
        else return ".";
    }

    public static File getFile(String filePath, String fileName) {
        if (filePath.equals("."))
            return new File(filePath + File.separator + fileName);
        return new File(File.separator + filePath + File.separator + fileName);
    }

    public static void writeToFile(InputStream input, File out) {
        if (input != null) {
            FileOutputStream output = null;
            try {
                out.getParentFile().mkdirs();
                output = new FileOutputStream(out);
                byte[] buf = new byte[8192];
                int length;
                while ((length = input.read(buf)) > 0) {
                    output.write(buf, 0, length);
                }
            } catch (Exception ignored) {
            } finally {
                try {
                    input.close();
                } catch (Exception ignored) {
                }
                try {
                    if (output != null)
                        output.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
