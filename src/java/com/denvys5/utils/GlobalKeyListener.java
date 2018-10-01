package com.denvys5.utils;

import com.denvys5.DenvysKeyListener;
import com.denvys5.DenvysLib;
import com.denvys5.DenvysLogger;
import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

public class GlobalKeyListener implements HotKeyListener {

    @Override
    public void onHotKey(HotKey hotKey) {
        if(DenvysLib.allowSameHotkey){
            KeyThread keyThread = new KeyThread(hotKey);
            keyThread.start();
        }else{
            DenvysLib.serialConnector.send(1,(byte)1, 2);
            if(DenvysLib.debug) DenvysLogger.logger.info("The pressed key is " + hotKey.toString());
        }
    }

    public class KeyThread extends Thread {
        private long timer = 0;
        private int counter = 0;
        private HotKey hotKey;
        private boolean flag = false;
        public KeyThread(HotKey hotKey){
            super();
            this.hotKey = hotKey;
        }
        public void run(){
            if(counter == 1){
                timer = System.currentTimeMillis();
            }
            if(counter >= DenvysLib.maxButtonPresses){
                counter = 0;
                if(System.currentTimeMillis() - timer < DenvysLib.maxButtonPressTime){
                    DenvysKeyListener.provider.stop();
                    flag = true;
                    try {
                        Thread.sleep(DenvysLib.maxButtonPressTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            counter++;
            if(!flag) {
                DenvysKeyListener.provider.reset();
                DenvysKeyListener.robot.keyPress(hotKey.keyStroke.getKeyCode());
                DenvysKeyListener.robot.keyRelease(hotKey.keyStroke.getKeyCode());
                DenvysKeyListener.provider.register(hotKey.keyStroke, GlobalKeyListener.this);
                DenvysLib.serialConnector.send(1,(byte)1, 2);
                if (DenvysLib.debug) DenvysLogger.logger.debug("The key pressed " + hotKey);
            }else{
                DenvysKeyListener.provider = Provider.getCurrentProvider(true);
                DenvysKeyListener.provider.register(hotKey.keyStroke, GlobalKeyListener.this);
                flag = false;
            }
        }


    }
}
