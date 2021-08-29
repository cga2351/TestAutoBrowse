package com.example.test_auto_browse.io.appium.android.bootstrap;

import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.example.test_auto_browse.utils.Logger;

import com.example.test_auto_browse.io.appium.android.bootstrap.exceptions.InvalidCoordinatesException;
import com.example.test_auto_browse.io.appium.android.bootstrap.utils.Point;
import com.example.test_auto_browse.io.appium.uiautomator.core.UiAutomatorBridge;

public abstract class PositionHelper {
  
  /**
   * Given a position, it will return either the position based on percentage
   * (by passing in a double between 0 and 1) or absolute position based on the
   * coordinates entered.
   *
   * @param pointCoord The position to translate.
   * @param length Length of side to use for percentage positions.
   * @param offset Position offset.
   * @return
   */
  private static double translateCoordinate(double pointCoord, double length, double offset) {
    double translatedCoord = 0;
    
    if (pointCoord == 0) {
      translatedCoord = length * 0.5;
    } else if (Math.abs(pointCoord) > 0 && Math.abs(pointCoord) < 1) {
      translatedCoord = length * pointCoord;
    } else {
      translatedCoord = pointCoord;
    }
    
    return translatedCoord + offset;
  }
  
  /**
   * Translates coordinates relative to an element rectangle into absolute coordinates.
   *
   * @param point A point in relative coordinates.
   * @param displayRect The display rectangle to which the point is relative.
   * @param offsets X and Y values by which to offset the point. These are typically
   * the absolute coordinates of the display rectangle.
   * @param shouldCheckBounds Throw if the translated point is outside displayRect?
   * @return
   * @throws UiObjectNotFoundException
   * @throws InvalidCoordinatesException
   */
  public static Point getAbsolutePosition(final Point point, final Rect displayRect, 
                                          final Point offsets, final boolean shouldCheckBounds)
      throws UiObjectNotFoundException, InvalidCoordinatesException {
    final Point absolutePosition = new Point();
    
    absolutePosition.x = translateCoordinate(point.x, displayRect.width(), offsets.x);
    absolutePosition.y = translateCoordinate(point.y, displayRect.height(), offsets.y);
    
    if (shouldCheckBounds &&
        !displayRect.contains(absolutePosition.x.intValue(), absolutePosition.y.intValue())) {
      throw new InvalidCoordinatesException("Coordinate " + absolutePosition.toString() +
          " is outside of element rect: " + displayRect.toShortString());
    }
    Logger.debug("getAbsolutePosition(), absolutePosition: " + absolutePosition.toString());
    return absolutePosition;
  }

  public static Point getDeviceAbsPos(final Point point)
      throws UiObjectNotFoundException, InvalidCoordinatesException {
    final UiDevice d = UiDevice.getInstance();

    // get real display rect to return the point
    int displayWidth = d.getDisplayWidth();
    int displayHeight = d.getDisplayWidth();

    DisplayMetrics displayRealMetrics = new DisplayMetrics();
    UiAutomatorBridge.getInstance().getDefaultDisplay().getRealMetrics(displayRealMetrics);
    displayWidth = displayRealMetrics.widthPixels;
    displayHeight = displayRealMetrics.heightPixels;

    final Rect displayRect = new Rect(0, 0, displayWidth, displayHeight);

    Logger.debug("Display bounds: " + displayRect.toShortString());
    
    return getAbsolutePosition(point, displayRect, new Point(), true);
  }
}
