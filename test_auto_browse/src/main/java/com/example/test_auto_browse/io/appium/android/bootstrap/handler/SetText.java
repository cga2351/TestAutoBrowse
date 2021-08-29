package com.example.test_auto_browse.io.appium.android.bootstrap.handler;

import android.util.Log;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.utils.Logger;

import org.json.JSONException;

import java.util.Hashtable;

import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommand;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommandResult;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidElement;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidElementsHash;
import com.example.test_auto_browse.io.appium.android.bootstrap.CommandHandler;
import com.example.test_auto_browse.io.appium.android.bootstrap.WDStatus;
import com.example.test_auto_browse.io.appium.android.bootstrap.exceptions.ElementNotFoundException;

/**
 * This handler is used to set text in elements that support it.
 *
 */
public class SetText extends CommandHandler {

  /*
   * @param command The {@link AndroidCommand} used for this handler.
   *
   * @return {@link AndroidCommandResult}
   *
   * @throws JSONException
   *
   * @see io.appium.android.bootstrap.CommandHandler#execute(io.appium.android.
   * bootstrap.AndroidCommand)
   */
  @Override
  public AndroidCommandResult execute(final AndroidCommand command)
      throws JSONException {

    Logger.debug("-mqmsdebug", "SetText.execute() entry");

    AndroidElement el = null;
    if (command.isElementCommand()) {
      Logger.debug("Using element passed in.");
      el = command.getElement();
    } else {
      try {
        Logger.debug("Using currently-focused element.");
        AndroidElementsHash  elements = AndroidElementsHash.getInstance();
        el = elements.getElement(new UiSelector().focused(true), "");
      } catch (ElementNotFoundException e) {
        Logger.debug("-mqmsdebug", "SetText.execute(), ElementNotFoundException, e.getMessage()=" + e.getMessage());
        Logger.debug("Error retrieving focused element: " + e);
        return getErrorResult("Unable to set text without a focused element.");
      }
    }
    try {
      final Hashtable<String, Object> params = command.params();
      boolean replace = Boolean.parseBoolean(params.get("replace").toString());
      String text = params.get("text").toString();
      boolean pressEnter = false;
      if (text.endsWith("\\n")) {
        pressEnter = true;
        text = text.replace("\\n", "");
        Logger.debug("Will press enter after setting text");
      }
      boolean unicodeKeyboard = false;
      if (params.get("unicodeKeyboard") != null) {
        unicodeKeyboard = Boolean.parseBoolean(params.get("unicodeKeyboard").toString());
      }
      String currText = el.getText();
      new Clear().execute(command);
      if (!el.getText().isEmpty()) {
        // clear could have failed, or we could have a hint in the field
        // we'll assume it is the latter
        Logger.debug("Text not cleared. Assuming remainder is hint text.");
        currText = "";
      }
      if (!replace) {
        text = currText + text;
      }
      final boolean result = el.setText(text, unicodeKeyboard);
      if (!result) {
        Logger.debug("-mqmsdebug", "SetText.execute(), return getErrorResult(\"el.setText() failed!\")");
        return getErrorResult("el.setText() failed!");
      }
      if (pressEnter) {
        final UiDevice d = UiDevice.getInstance();
        d.pressEnter();
      }
      Logger.debug("-mqmsdebug", "SetText.execute(), return getSuccessResult(result), result=" + result);
      return getSuccessResult(result);
    } catch (final UiObjectNotFoundException e) {
      Logger.debug("-mqmsdebug", "SetText.execute(), UiObjectNotFoundException, e.getMessage()=" + e.getMessage());
      return new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT,
          e.getMessage());
    } catch (final Exception e) { // handle NullPointerException
      Logger.debug("-mqmsdebug", "SetText.execute(), Exception, e.getMessage()=" + e.getMessage());
      e.printStackTrace();
      return getErrorResult("Unknown error");
    }
  }
}
