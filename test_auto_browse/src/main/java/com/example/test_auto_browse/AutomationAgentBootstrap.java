package com.example.test_auto_browse;

import android.util.Size;

import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.example.test_auto_browse.utils.SysUtil;

public class AutomationAgentBootstrap extends UiAutomatorTestCase {
    public void testRunTask() {
        AutoBrowseApp.getInstance().run();
    }
}
