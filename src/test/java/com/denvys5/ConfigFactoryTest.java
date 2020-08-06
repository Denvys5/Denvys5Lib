package com.denvys5;

import org.junit.Before;
import org.junit.Test;


import java.io.File;

import static org.junit.Assert.*;

public class ConfigFactoryTest {
    private final String filename = "configuration.cfg";

    @Before
    public void setUp() {
        File file = new File(Utils.getRelativeFilepath() + filename);
        file.delete();
    }

    @Test
    public void testFilePath(){
        System.out.println(Utils.getRelativeFilepath());
    }

    @Test
    public void testGetConfig1() {
        String absolutePath = Utils.getRelativeFilepath();
        System.out.println(absolutePath);
        testCase(ConfigFactory.getConfig(absolutePath, filename));
    }

    @Test
    public void testGetConfig2() {
        testCase(ConfigFactory.getConfig(filename));
    }

    @Test
    public void testGetConfig3() {
        testCase(ConfigFactory.getConfig());
    }

    @Test
    public void getConfigWithRelativeFilePath() {
        testCase(ConfigFactory.getConfigWithRelativeFilePath(".", filename));
    }

    private void testCase(Config config){
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