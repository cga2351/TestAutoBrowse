package com.example.test_auto_browse.io.appium.android.bootstrap.handler;

import com.android.uiautomator.core.UiDevice;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommand;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommandResult;
import com.example.test_auto_browse.io.appium.android.bootstrap.CommandHandler;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This handler is used to get the size of the screen.
 *
 */
public class GetDeviceSize extends CommandHandler {

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
  public AndroidCommandResult execute(final AndroidCommand command) {
    if (!command.isElementCommand()) {
      // only makes sense on a device
      final UiDevice d = UiDevice.getInstance();
      final JSONObject res = new JSONObject();
      try {
        res.put("height", d.getDisplayHeight());
        res.put("width", d.getDisplayWidth());
      } catch (final JSONException e) {
        getErrorResult("Error serializing height/width data into JSON");
      }
      return getSuccessResult(res);
    } else {
      return getErrorResult("Unable to get device size on an element.");
    }
  }
}
