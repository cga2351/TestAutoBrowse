package com.example.test_auto_browse.io.appium.uiautomator.core;

import com.android.uiautomator.core.*;
import android.view.accessibility.*;
import com.example.test_auto_browse.io.appium.android.bootstrap.utils.*;

public abstract class AccessibilityNodeInfoGetter
{
    private static Configurator configurator;
    
    static {
        AccessibilityNodeInfoGetter.configurator = Configurator.getInstance();
    }
    
    public static AccessibilityNodeInfo fromUiObject(final UiObject uiObject) {
        return (AccessibilityNodeInfo)ReflectionUtils.invoke(ReflectionUtils.method(UiObject.class, "findAccessibilityNodeInfo", Long.TYPE), uiObject, AccessibilityNodeInfoGetter.configurator.getWaitForSelectorTimeout());
    }
}
