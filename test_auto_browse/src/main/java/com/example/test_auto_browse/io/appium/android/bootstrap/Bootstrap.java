package com.example.test_auto_browse.io.appium.android.bootstrap;

import android.util.Log;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.example.test_auto_browse.utils.Logger;

import java.io.IOException;

import com.example.test_auto_browse.io.appium.android.bootstrap.exceptions.SocketServerException;
import com.example.test_auto_browse.io.appium.android.bootstrap.handler.Find;

/**
 * The Bootstrap class runs the socket server.
 *
 */
public class Bootstrap extends UiAutomatorTestCase {

  public void testRunServer() {
    Logger.debug("io.appium.android.bootstrap.Bootstrap.testRunServer(), entry");
    Find.params = getParams();
    boolean disableAndroidWatchers = Boolean.parseBoolean(getParams().getString("disableAndroidWatchers"));
    Logger.debug("-mqmsdebug", "forward cmd Bootstrap.testRunServer(), disableAndroidWatchers=" + disableAndroidWatchers);
    SocketServer server = null;
    try {
      server = new SocketServer(4724);
      server.listenForever(disableAndroidWatchers);
    } catch (SocketServerException e) {
      Logger.error(e.getError());
      Logger.debug("-mqmsdebug", "Bootstrap.testRunServer(), System.exit(1)");
      System.exit(1);
    }
  }
}
