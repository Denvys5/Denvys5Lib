package com.denvys5;

import org.junit.Test;

import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class ConfigFactoryTest {

    @Test
    public void testFilePath(){
        try {
            System.out.println(ConfigFactory.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}