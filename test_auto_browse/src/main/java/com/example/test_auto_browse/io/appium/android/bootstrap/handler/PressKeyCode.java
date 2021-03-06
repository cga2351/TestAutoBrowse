package com.example.test_auto_browse.io.appium.android.bootstrap.handler;

import com.android.uiautomator.core.UiDevice;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommand;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommandResult;
import com.example.test_auto_browse.io.appium.android.bootstrap.CommandHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This handler is used to PressKeyCode.
 *
 */
public class PressKeyCode extends CommandHandler {
  public Integer keyCode;
  public Integer metaState;

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
    try {
      final Hashtable<String, Object> params = command.params();
      Object kc = params.get("keycode");
      if (kc instanceof Integer) {
        keyCode = (Integer) kc;
      } else if (kc instanceof String) {
        keyCode = Integer.parseInt((String) kc);
      } else {
        throw new IllegalArgumentException("Keycode of type " + kc.getClass() + "not supported.");
      }

      if (params.get("metastate") != JSONObject.NULL) {
        metaState = (Integer) params.get("metastate");
        UiDevice.getInstance().pressKeyCode(keyCode, metaState);
      } else {
        UiDevice.getInstance().pressKeyCode(keyCode);
      }

      return getSuccessResult(true);
    } catch (final Exception e) {
      return getErrorResult(e.getMessage());
    }
  }
}
