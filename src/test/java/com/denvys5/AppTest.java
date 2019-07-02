package com.denvys5;

import static org.junit.Assert.assertEquals;

import com.denvys5.utils.BaseUtils;
import org.junit.Test;

public class AppTest {
    @Test
    public void checkConfig(){
        Config.initConfig(".");
        BaseUtils.setProperty("hello", "world");
        BaseUtils.setProperty("hello", "world2");
        BaseUtils.setProperty("hell0", "world2");
        BaseUtils.setProperty("hEllo", "world3");
        BaseUtils.setProperty("123", 129);

        assertEquals(130, BaseUtils.getPropertyInt("124", 130));
        assertEquals(129, BaseUtils.getPropertyInt("123", 130));
        assertEquals("world3", BaseUtils.getPropertyString("hEllo"));
    }
}
