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