package com.denvys5;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class AppTest {
    private Config config;

    @Before
    public void setup(){
        String filename = "settings.cfg";
        File file = new File(Utils.getRelativeFilepath() + filename);
        file.delete();
        config = ConfigFactory.getConfig(filename);
    }

    @Test
    public void checkConfig(){
        config.setProperty("hello", "world");
        config.setProperty("hello", "world2");
        config.setProperty("hell0", "world2");
        config.setProperty("hEllo", "world3");
        config.setProperty("123", 129);

        assertEquals(130, config.getPropertyInt("124", 130));
        assertEquals(129, config.getPropertyInt("123", 130));
        assertEquals("world3", config.getPropertyString("hEllo"));
    }
}
