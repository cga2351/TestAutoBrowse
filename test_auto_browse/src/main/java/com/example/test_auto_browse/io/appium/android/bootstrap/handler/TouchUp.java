package com.example.test_auto_browse.io.appium.android.bootstrap.handler;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.example.test_auto_browse.utils.Logger;

import com.example.test_auto_browse.io.appium.uiautomator.core.UiAutomatorBridge;

/**
 * This handler is used to perform a touchUp event on an element in the Android
 * UI.
 * 
 */
public class TouchUp extends TouchEvent {

  @Override
  protected boolean executeTouchEvent() throws UiObjectNotFoundException {
    printEventDebugLine("TouchUp");
    try {
      return UiAutomatorBridge.getInstance().getInteractionController().touchUp(clickX, clickY);
    } catch (final Exception e) {
      Logger.debug("Problem invoking touchUp: " + e);
      return false;
    }
  }
}