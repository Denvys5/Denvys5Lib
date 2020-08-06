package com.denvys5;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Optional;

class Utils {
    public static String getRelativeFilepath(){
        Optional<String> optionalPath = Optional.of(ClassLoader.getSystemClassLoader().getResource(".").getPath());
        return optionalPath.orElse(".");
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
            } catch (Exception e) {
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
