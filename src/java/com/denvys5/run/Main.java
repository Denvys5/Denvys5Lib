package com.denvys5.run;

import com.denvys5.DenvysKeyListener;
import com.denvys5.DenvysLib;
import com.denvys5.utils.BaseUtils;

public class Main {

    public static void main(String[] args) {
        DenvysLib.initSerial(null, null);
        DenvysKeyListener.init();
        DenvysLib.initConfig(".");
        BaseUtils.setProperty("hello", "world");
        BaseUtils.setProperty("hello", "world2");
        BaseUtils.setProperty("hell0", "world2");
        BaseUtils.setProperty("hEllo", "world3");
        BaseUtils.setProperty("123", 129);
        System.out.println(BaseUtils.getPropertyInt("123", 130));
        System.out.println(BaseUtils.getPropertyInt("124", 130));
        System.out.println(BaseUtils.getPropertyString("hEllo"));
    }
}
