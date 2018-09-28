package com.denvys5.utils;

import com.denvys5.DenvysLib;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;


public class GuardUtils {
    public static boolean ret = false;
    public static List<URL> url = new ArrayList<URL>();
    static long filesize = 0;

    public static void check() {
        if (GuardUtils.checkProcesses(DenvysLib.p)) {
            try {
                Class<?> af = Class.forName("java.lang.Shutdown");
                Method m = af.getDeclaredMethod("halt0", int.class);
                m.setAccessible(true);
                m.invoke(null, 1);
            } catch (Exception e) {
            }
        }
    }

    public static void delete(File file) {
        try {
            if (!file.exists()) return;
            if (file.isDirectory()) {
                for (File f : file.listFiles()) delete(f);
                file.delete();
            } else file.delete();
        } catch (Exception e) {
        }
    }

    public static void getLogs(File Logs) {
        if (!Logs.exists()) Logs.mkdirs();
        for (File file : Logs.listFiles()) {
            if (file.isDirectory()) {
            } else {
                if (file.getName().contains(".log")) {
                    delete(file);
                }
            }
        }
    }

    public static String hash(URL url) {
        if (url == null) {
            return "h";
        } else if (urltofile(url).isDirectory()) {
            return "d";
        } else {
            InputStream IS = null;
            DigestInputStream DI = null;
            BufferedInputStream BS = null;
            Formatter F = null;

            try {
                MessageDigest MD = MessageDigest.getInstance("MD5");
                IS = url.openStream();
                BS = new BufferedInputStream(IS);
                DI = new DigestInputStream(BS, MD);

                while (DI.read() != -1) {
                }

                byte[] Md = MD.digest();
                F = new Formatter();
                byte[] Mi = Md;
                int I = Md.length;

                for (int i = 0; i < I; ++i) {
                    byte Bi = Mi[i];
                    F.format("%02x", new Object[]{Byte.valueOf(Bi)});
                }

                String str = F.toString();
                return str;
            } catch (Exception e) {
            } finally {
                try {
                    IS.close();
                    IS = null;
                } catch (Exception e) {
                }

                try {
                    DI.close();
                    DI = null;
                } catch (Exception e) {
                }

                try {
                    BS.close();
                    BS = null;
                } catch (Exception e) {
                }

                try {
                    F.close();
                    F = null;
                } catch (Exception e) {
                }

            }

            return "h";
        }
    }

    public static File urltofile(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException var2) {
            return new File(url.getPath().replace("file:/", "").replace("file:", ""));
        }
    }

    public static boolean checkProcesses(String[] onlineData) {
        if (onlineData == null) return false;

        try {
            int platform = BaseUtils.getPlatform();
            String line;
            Process p;
            List<String> processes = new ArrayList<>();

            if (platform == 2)
                p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe /v /fo list");
            else p = Runtime.getRuntime().exec("ps -e");

            try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

                while ((line = input.readLine()) != null) {
                    processes.add(line.toLowerCase());
                }

            }

            for (String process : processes) {
                for (String Data : onlineData) {
                    if (process.contains(Data.toLowerCase())) {
                        return true;
                    }
                }
            }

        } catch (Exception e) {
        }

        return false;
    }
}
