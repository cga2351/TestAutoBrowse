package com.android.uiautomator.core;

import android.app.UiAutomation;
import android.view.Display;


import com.example.test_auto_browse.utils.Logger;

import java.util.concurrent.TimeoutException;

public class CustomizedUiAutomatorBridge extends UiAutomatorBridge  {

    UiAutomatorBridge originUiAutomatorBridge;
    UiAutomation originUiAutomation;
    ShellUiAutomatorBridge shellUiAutomatorBridge;

    public CustomizedUiAutomatorBridge(UiAutomation uiAutomation, UiAutomatorBridge uiAutomatorBridge) {
        super(uiAutomation);
        originUiAutomatorBridge = uiAutomatorBridge;
        originUiAutomation = uiAutomation;
    }

    @Override
    public void waitForIdle(long timeout) {
//        Logger.debug("-mqmsdebug", "CustomizedUiAutomatorBridge.waitForIdle(), entry");
        try {
//            Logger.debug("-mqmsdebug", "CustomizedUiAutomatorBridge.waitForIdle(), entry");
            originUiAutomation.waitForIdle(10, timeout);
//            Logger.debug("-mqmsdebug", "CustomizedUiAutomatorBridge.waitForIdle(), exit");
        } catch (TimeoutException te) {
            Logger.error("-mqmsdebug", "Could not detect idle state. info:" + te.getMessage());
        }
    }

    @Override
    public int getRotation() {
        return originUiAutomatorBridge.getRotation();
    }

    @Override
    public boolean isScreenOn() {
        return originUiAutomatorBridge.isScreenOn();
    }

    @Override
    public Display getDefaultDisplay() {
        return originUiAutomatorBridge.getDefaultDisplay();
    }

    @Override
    public long getSystemLongPressTime() {
        return originUiAutomatorBridge.getSystemLongPressTime();
    }
}
