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
