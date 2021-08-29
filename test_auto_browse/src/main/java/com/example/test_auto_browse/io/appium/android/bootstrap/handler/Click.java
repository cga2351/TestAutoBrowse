package com.example.test_auto_browse.io.appium.android.bootstrap.handler;

import android.os.SystemClock;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.utils.Logger;

import org.json.JSONException;

import java.util.Hashtable;

import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommand;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidCommandResult;
import com.example.test_auto_browse.io.appium.android.bootstrap.AndroidElement;
import com.example.test_auto_browse.io.appium.android.bootstrap.CommandHandler;
import com.example.test_auto_browse.io.appium.android.bootstrap.PositionHelper;
import com.example.test_auto_browse.io.appium.android.bootstrap.WDStatus;
import com.example.test_auto_browse.io.appium.android.bootstrap.exceptions.InvalidCoordinatesException;
import com.example.test_auto_browse.io.appium.android.bootstrap.utils.Point;
import com.example.test_auto_browse.io.appium.uiautomator.core.UiAutomatorBridge;

/**
 * This handler is used to click elements in the Android UI.
 * 
 * Based on the element Id, click that element.
 * 
 */
public class Click extends CommandHandler {

  /*
   * @param command The {@link AndroidCommand}
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
    if (command.isElementCommand()) {
      Logger.debug("-mqmsdebug", "Click.execute(), element click");
      try {
        final AndroidElement el = command.getElement();
        el.click();
        return getSuccessResult(true);
      } catch (final UiObjectNotFoundException e) {
        Logger.debug("-mqmsdebug", "Click.execute(), catch UiObjectNotFoundException");
        return new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT, e.getMessage());
      } catch (final Exception e) {
        // handle NullPointerException
        Logger.debug("-mqmsdebug", "Click.execute(), catch Exception, info:" + e.getMessage());
        return getErrorResult("Unknown error");
      }
    } else {
      Logger.debug("-mqmsdebug", "Click.execute(), coordinates click");
      final Hashtable<String, Object> params = command.params();
      Point coords = new Point(Double.parseDouble(params.get("x").toString()),
          Double.parseDouble(params.get("y").toString()) );
      
      try {
        coords = PositionHelper.getDeviceAbsPos(coords);
      } catch (final UiObjectNotFoundException e) {
        return new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT,
            e.getMessage());
      } catch (final InvalidCoordinatesException e) {
        return new AndroidCommandResult(WDStatus.INVALID_ELEMENT_COORDINATES,
            e.getMessage());
      }

      // change appium coordinate click, do not call UiDevice.click() directly, if the coordinate
      // is in the navigation bar, it will not perform click action, and the UiDevice.click() function
      // will call InteractionController.touchDown and touchUp to emulate the click, so, we call
      // InteractionController.touchDown and touchUp directly

//      final boolean res = UiDevice.getInstance().click(coords.x.intValue(), coords.y.intValue());
      String errorMsg = Constant.RES_FAILED;

      if (UiAutomatorBridge.getInstance().getInteractionController().touchDown(coords.x.intValue(), coords.y.intValue())) {
        SystemClock.sleep(100);
        if (UiAutomatorBridge.getInstance().getInteractionController().touchUp(coords.x.intValue(), coords.y.intValue())) {
          return getSuccessResult(true);
        } else {
          errorMsg = "Click.execute(), interactionController.touchUp() failed";
        }
      } else {
        errorMsg = "Click.execute(), interactionController.touchDown() failed";
      }

      return getErrorResult(errorMsg);
    }
  }
}
