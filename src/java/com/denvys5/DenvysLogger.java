package com.denvys5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DenvysLogger {
    public static Logger logger;
    public static void initLogger(){
        if(DenvysLib.debug) logger = LogManager.getLogger(DenvysLib.class);
    }
}
