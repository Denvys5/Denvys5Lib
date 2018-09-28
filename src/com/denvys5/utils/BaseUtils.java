package com.denvys5.utils;

import com.denvys5.DenvysLib;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseUtils {
    public static final String empty = "";
    public static ConfigUtils config = new ConfigUtils(DenvysLib.configName, getConfigName());

    public static Map<String, BufferedImage> imgs = new HashMap<String, BufferedImage>();

    public static BufferedImage getLocalImage(String name) {
        try {
            if (imgs.containsKey(name)) return (BufferedImage) imgs.get(name);

            BufferedImage img = ImageIO.read(BaseUtils.class.getResource("/com/denvys5/theme/" + name + ".png"));
            imgs.put(name, img);
            send("Opened local image: " + name + ".png");
            return img;
        } catch (Exception e) {
            sendErr("Fail to open local image: " + name + ".png");
            return getEmptyImage();
        }
    }

    public static BufferedImage getEmptyImage() {
        return new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);
    }

    public static void send(String msg) {
        if (DenvysLib.debug) System.out.println(date() + "[Launcher thread/INFO]: " + msg);
    }

    public static void sendErr(String err) {
        if (DenvysLib.debug) System.err.println(date() + "[Launcher thread/WARN]: " + err);
    }

    public static void sendp(String msg) {
        if (DenvysLib.debug) System.out.println(msg);
    }

    public static void sendErrp(String err) {
        if (DenvysLib.debug) System.err.println(err);
    }

    public static boolean contains(int x, int y, int xx, int yy, int w, int h) {
        return (x >= xx) && (y >= yy) && (x < xx + w) && (y < yy + h);
    }

    public static File getConfigName() {
        if (DenvysLib.baseconf.equals("."))
            return new File(DenvysLib.baseconf + File.separator + DenvysLib.configName);
        String home = System.getProperty("user.home", "");
        String path = File.separator + DenvysLib.baseconf + File.separator + DenvysLib.configName;
//        if(Settings.baseconf.contains(".."))
//            return new File(path);
        return new File(home, path);
    }


    public static int getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) return 2;
        if (osName.contains("mac")) return 3;
        if (osName.contains("solaris")) return 1;
        if (osName.contains("sunos")) return 1;
        if (osName.contains("linux")) return 0;
        if (osName.contains("unix")) return 0;
        return 4;
    }

    public static String buildUrl(String s) {
        return DenvysLib.http + DenvysLib.domain + "/" + DenvysLib.siteDir + "/" + s;
    }

    static {
        config.load();
    }

    public static void setProperty(String s, Object value) {
        if (config.checkProperty(s)) config.changeProperty(s, value);
        else config.put(s, value);
    }

    public static String getPropertyString(String s) {
        if (config.checkProperty(s)) return config.getPropertyString(s);
        return null;
    }

    public static boolean getPropertyBoolean(String s) {
        if (config.checkProperty(s)) return config.getPropertyBoolean(s);
        return false;
    }

    public static int getPropertyInt(String s) {
        if (config.checkProperty(s)) return config.getPropertyInteger(s);
        return 0;
    }

    public static int getPropertyInt(String s, int d) {
        if (config.checkProperty(s)) return config.getPropertyInteger(s);
        return d;
    }

    public static boolean getPropertyBoolean(String s, boolean b) {
        if (config.checkProperty(s)) return config.getPropertyBoolean(s);
        return b;
    }

    public static String getPropertyString(String s, String str) {
        if (config.checkProperty(s)) return config.getPropertyString(s);
        return str;
    }

    public static String runHTTP(String URL, String params, boolean send) {
        HttpURLConnection ct = null;
        try {

            URL url = new URL(URL + params);
            ct = (HttpURLConnection) url.openConnection();
            ct.setRequestMethod("GET");
            ct.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            ct.setRequestProperty("Content-Length", "0");
            ct.setRequestProperty("Content-Language", "en-US");
            ct.setUseCaches(false);
            ct.setDoInput(true);
            ct.setDoOutput(true);

            ct.connect();

            InputStream is = ct.getInputStream();
            StringBuilder response;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }
            }

            String str = response.toString();

            return str;
        } catch (Exception e) {
            return null;
        } finally {
            if (ct != null) ct.disconnect();
        }
    }

    public static String getURLSc(String script) {
        return getURL("/" + DenvysLib.siteDir + "/" + script);
    }


    public static String getURL(String path) {
        return DenvysLib.http + DenvysLib.domain + path;
    }


    public static void openURL(String url) {
        try {
            Object o = Class.forName("java.awt.Desktop").getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            o.getClass().getMethod("browse", new Class[]{URI.class}).invoke(o, new Object[]{new URI(url)});
        } catch (Throwable e) {
        }
    }

    public static void deleteDirectory(File file) {
        if (!file.exists()) return;
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                deleteDirectory(f);
            file.delete();
        } else file.delete();
    }


    public static String execute(String surl, Object[] params) {
        try {
            send("Openning stream: " + surl);
            URL url = new URL(surl);

            InputStream is = PostUtils.post(url, params);
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            String str1 = response.toString().trim();
            send("Stream opened for " + surl + " completed, return answer: ");
            return str1;
        } catch (Exception e) {
            sendErr("Stream for " + surl + " not ensablished, return null");
            return null;
        }
    }


    public static int servtype = 2;

    public static String[] pollServer(String ip, int port) {
        Socket soc = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        try {
            soc = new Socket();
            soc.setSoTimeout(6000);
            soc.setTcpNoDelay(true);
            soc.setTrafficClass(18);
            soc.connect(new InetSocketAddress(ip, port), 6000);
            dis = new DataInputStream(soc.getInputStream());
            dos = new DataOutputStream(soc.getOutputStream());
            dos.write(254);

            if (dis.read() != 255) {
                throw new IOException("Bad message");
            }
            String servc = readString(dis, 256);
            servc.substring(3);
            if (servc.substring(0, 1).equalsIgnoreCase("§") && servc.substring(1, 2).equalsIgnoreCase("1")) {
                servtype = 1;
                return servc.split("\u0000");

            } else {
                servtype = 2;
                return servc.split("§");
            }

        } catch (Exception e) {
            return new String[]{null, null, null};
        } finally {
            try {
                dis.close();
            } catch (Exception e) {
            }
            try {
                dos.close();
            } catch (Exception e) {
            }
            try {
                soc.close();
            } catch (Exception e) {
            }
        }
    }

    public static int parseInt(String integer, int def) {
        try {
            return Integer.parseInt(integer.trim());
        } catch (Exception e) {
            return def;
        }
    }

    public static String readString(DataInputStream is, int d) throws IOException {
        short word = is.readShort();
        if (word > d) throw new IOException();
        if (word < 0) throw new IOException();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < word; i++) {
            res.append(is.readChar());
        }
        return res.toString();
    }

    public static String unix2hrd(long unix) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(unix * 1000));
    }

    public void delete(File file) {
        if (!file.exists()) return;

        if (file.isDirectory()) {
            for (File f : file.listFiles()) delete(f);
            file.delete();
        } else {
            file.delete();
        }
    }

    public static boolean checkLink(String l) {
        if (l.contains("#")) {
            return false;
        }
        return true;
    }

    public static boolean existLink(String l) {
        if (l.contains("@")) {
            return true;
        }
        return false;
    }

    private static String date() {
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("[HH:mm:ss]");
        String s = formatter.format(now) + " ";
        return s;
    }
}
