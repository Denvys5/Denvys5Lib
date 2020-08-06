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
import org.junit.runners.model.TestClass;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class ConfigFactoryJsonTest {
    private final String filename = "test.json";
    private final TestJson testJson = new TestJson();

    @Before
    public void setUp() throws Exception {
        File file = new File(Utils.getRelativeFilepath() + filename);
        file.delete();
    }

    class TestJson{
        String first = "Hello World";
        List<String> words = new ArrayList();

        public TestJson() {
            this.words.addAll(Arrays.asList("Hello", "World", "!"));
        }

        @Override
        public String toString() {
            return "TestJson{" +
                    "first='" + first + '\'' +
                    ", words=" + words +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestJson testJson = (TestJson) o;
            return Objects.equals(first, testJson.first) &&
                    Objects.equals(words, testJson.words);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, words);
        }
    }

    @Test
    public void readAndSaveJsonObjectWithRelativeFilePath() {
        ConfigFactory.saveJsonObjectWithRelativeFilePath(filename, testJson);

        TestJson readJson = ConfigFactory.getJsonObjectWithRelativeFilePath(filename, TestJson.class);
        System.out.println(readJson);
        assertEquals(testJson, readJson);
    }

    @Test
    public void readAndSaveJsonObject() {
        ConfigFactory.saveJsonObject(Utils.getRelativeFilepath()+filename, testJson);

        TestJson readJson = ConfigFactory.getJsonObject(Utils.getRelativeFilepath()+filename, TestJson.class);
        System.out.println(readJson);
        assertEquals(testJson, readJson);
    }
}