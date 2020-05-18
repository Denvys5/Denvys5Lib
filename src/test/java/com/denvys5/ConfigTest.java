package com.denvys5;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ConfigTest {
    private Config config;

    @Before
    public void setUp() {
        String filename = "configuration.cfg";
        File file = new File(Utils.getRelativeFilepath() + filename);
        file.delete();
        config = ConfigFactory.getConfig(filename);
    }

    @Test
    public void isDebug() {
        assertEquals(DefaultParameters.DEFAULT_DEBUG, config.isDebug());
    }

    @Test
    public void addPropertyString() {
        String propertyName = "property";
        String propertyDefault = "B";
        config.addPropertyString(propertyName, propertyDefault);

        assertEquals(propertyDefault, config.getPropertyString(propertyName));
    }

    @Test
    public void addPropertyBoolean() {
        String propertyName = "property";
        boolean propertyDefault = true;
        config.addPropertyBoolean(propertyName, propertyDefault);

        assertEquals(propertyDefault, config.getPropertyBoolean(propertyName));
    }

    @Test
    public void addPropertyInt() {
        String propertyName = "property";
        int propertyDefault = 10;
        config.addPropertyInt(propertyName, propertyDefault);

        assertEquals(propertyDefault, config.getPropertyInt(propertyName));
    }

    @Test
    public void addPropertyDouble() {
        String propertyName = "property";
        double propertyDefault = 10D;
        config.addPropertyDouble(propertyName, propertyDefault);
        assertTrue(Math.abs(propertyDefault - config.getPropertyDouble(propertyName)) < 0.001);
    }

    @Test
    public void isSetProperty() {
        String propertyName = "property";
        double propertyDefault = 10D;
        config.addPropertyDouble(propertyName, propertyDefault);
        assertTrue(config.isSetProperty(propertyName));
    }

    @Test
    public void setProperty() {
        String propertyName = "property";
        int propertyDefault = 10;
        config.setProperty(propertyName, propertyDefault);

        assertEquals(propertyDefault, config.getPropertyInt(propertyName));


        propertyDefault = 20;
        config.setProperty(propertyName, propertyDefault);

        assertEquals(propertyDefault, config.getPropertyInt(propertyName));
    }

    @Test
    public void getPropertyInt() {
        String propertyName = "property";
        int propertyDefault = 10;
        config.addPropertyInt(propertyName, propertyDefault);

        assertEquals(propertyDefault, config.getPropertyInt(propertyName));
    }

    @Test
    public void testGetPropertyInt() {
        String propertyName = "property";
        int propertyDefault = 10;

        assertEquals(propertyDefault, config.getPropertyInt(propertyName, propertyDefault));
    }

    @Test
    public void getPropertyBoolean() {
        String propertyName = "property";
        boolean propertyDefault = true;
        config.addPropertyBoolean(propertyName, propertyDefault);

        assertEquals(propertyDefault, config.getPropertyBoolean(propertyName));
    }

    @Test
    public void testGetPropertyBoolean() {
        String propertyName = "property";
        boolean propertyDefault = true;

        assertEquals(propertyDefault, config.getPropertyBoolean(propertyName, propertyDefault));
    }

    @Test
    public void getPropertyString() {
        String propertyName = "property";
        String propertyDefault = "true";
        config.addPropertyString(propertyName, propertyDefault);

        assertEquals(propertyDefault, config.getPropertyString(propertyName));
    }

    @Test
    public void testGetPropertyString() {
        String propertyName = "property";
        String propertyDefault = "true";

        assertEquals(propertyDefault, config.getPropertyString(propertyName, propertyDefault));
    }

    @Test
    public void getPropertyDouble() {
        String propertyName = "property";
        double propertyDefault = 10D;
        config.addPropertyDouble(propertyName, propertyDefault);

        assertTrue(Math.abs(propertyDefault - config.getPropertyDouble(propertyName)) < 0.001);
    }

    @Test
    public void testGetPropertyDouble() {
        String propertyName = "property";
        double propertyDefault = 10D;

        assertTrue(Math.abs(propertyDefault - config.getPropertyDouble(propertyName, propertyDefault)) < 0.001);
    }
}