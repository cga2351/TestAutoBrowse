package com.example.test_auto_browse.io.appium.android.bootstrap.utils;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.utils.Logger;

public class TheWatchers {
  private static TheWatchers ourInstance = new TheWatchers();
  private boolean            alerted     = false;

  public static TheWatchers getInstance() {
    return ourInstance;
  }

  private TheWatchers() {
  }

  public boolean check() {
    // Send only one alert message...
    if (isDialogPresent() && (!alerted)) {
      Logger.error("Emitting system alert message");
      alerted = true;
    }

    // if the dialog went away, make sure we can send an alert again
    if (!isDialogPresent() && alerted) {
      alerted = false;
    }
    return alerted;
  }

  public boolean isDialogPresent() {
    UiObject alertDialog = new UiObject(
        new UiSelector().packageName("com.android.systemui"));
    return alertDialog.exists();
  }
}
