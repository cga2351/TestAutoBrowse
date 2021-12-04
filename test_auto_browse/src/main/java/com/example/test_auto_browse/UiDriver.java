package com.example.test_auto_browse;

import android.app.UiAutomation;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Size;
import android.view.KeyEvent;

import com.android.uiautomator.core.CustomizedUiAutomatorBridge;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.io.appium.android.bootstrap.utils.ReflectionUtils;
import com.example.test_auto_browse.io.appium.android.bootstrap.utils.XMLHierarchy;
import com.example.test_auto_browse.io.appium.uiautomator.core.UiAutomatorBridge;
import com.example.test_auto_browse.utils.DateUtil;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/5/19
 * description :
 */

public class UiDriver {
    private static UiDevice uiDevice = UiDevice.getInstance();
    private static Size screenSize = SysUtil.getScreenSize();
    private static int defaultTimeout;
    private static com.android.uiautomator.core.UiAutomatorBridge originUiAutomatorBridge;
    private static UiAutomation originUiAutomation;

    public static void init() {
        uiDevice = UiDevice.getInstance();
        screenSize = SysUtil.getScreenSize();

        if (Build.VERSION.SDK_INT < 26) {
            defaultTimeout = 5000;
//            defaultTimeout = 15000;
        } else {
            defaultTimeout = 5000;
        }

        changeUiAutomatorBridge();
    }

    private static void changeUiAutomatorBridge() {
        ///
        UiDevice device = UiDevice.getInstance();
        try {
            Field fieldAutomatorBridge = null;
            fieldAutomatorBridge = device.getClass().getDeclaredField("mUiAutomationBridge");

            fieldAutomatorBridge.setAccessible(true);
            Object tmpOriginUiAutomatorBridge = fieldAutomatorBridge.get(device);
            Logger.debug("-mqmsdebug", "changeUiAutomatorBridge(), tmpOriginUiAutomatorBridge=" + tmpOriginUiAutomatorBridge);
            originUiAutomatorBridge = (com.android.uiautomator.core.UiAutomatorBridge) tmpOriginUiAutomatorBridge;

            ///
            Field filedUiAutomation = originUiAutomatorBridge.getClass().getSuperclass().getDeclaredField("mUiAutomation");
            filedUiAutomation.setAccessible(true);
            Object tmpOriginUiAutomation = filedUiAutomation.get(originUiAutomatorBridge);
            Logger.debug("-mqmsdebug", "changeUiAutomatorBridge(), tmpOriginUiAutomation=" + tmpOriginUiAutomation);
            originUiAutomation = (UiAutomation) tmpOriginUiAutomation;

            ///
            CustomizedUiAutomatorBridge customizedUiAutomatorBridge = new CustomizedUiAutomatorBridge(originUiAutomation, originUiAutomatorBridge);
//            Field modifiersField = Field.class.getDeclaredField("modifiers");'
            try {
                Field accessFlags = Field.class.getDeclaredField("accessFlags");
                accessFlags.setAccessible(true);
                accessFlags.set(fieldAutomatorBridge, fieldAutomatorBridge.getModifiers() & ~Modifier.FINAL);
            } catch (NoSuchFieldException e) {
                Logger.error("-mqmsdebug", "changeUiAutomatorBridge(), NoSuchFieldException, info:" + e.getMessage());
//                e.printStackTrace();
            }
            fieldAutomatorBridge.set(device, customizedUiAutomatorBridge);

            Object newAutomatorBridge = ReflectionUtils.getField("mUiAutomationBridge", device);
            Logger.debug("-mqmsdebug", "changeUiAutomatorBridge(), newAutomatorBridge=" + newAutomatorBridge + ", customizedUiAutomatorBridge=" + customizedUiAutomatorBridge);
        } catch (NoSuchFieldException e) {
            Logger.error("-mqmsdebug", "changeUiAutomatorBridge(), NoSuchFieldException, info:" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Logger.error("-mqmsdebug", "changeUiAutomatorBridge(), IllegalAccessException, info:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveScreenshot(File saveFile) {
        boolean result = uiDevice.takeScreenshot(saveFile);
        Logger.debug("saveScreenshot(), save screenshot result = " + result + ", saveFile = " + saveFile.getAbsolutePath());
    }

    public static void saveDebugScreenshot(String fileDescription) throws InterruptedException {
        saveDebugScreenshotWithDelay(fileDescription, 0);
    }

    public static void saveDebugScreenshotWithDelay(String fileDescription, int delay) throws InterruptedException {
        Thread.sleep(delay);
        String savePath = Constant.SD_PATH_DEBUG_SCREENSHOTS
                + DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE, System.currentTimeMillis())
                + "_" + fileDescription
                + ".jpg";
        UiDriver.saveScreenshot(new File(savePath));
    }

    public static UiObject find(UiSelector uiSelector)  throws InterruptedException  {
        return findInVisibleRect(uiSelector, defaultTimeout);
    }

    public static UiObject find(UiSelector uiSelector, long timeout) throws InterruptedException {
        return findInVisibleRect(uiSelector, timeout);
    }

    public static UiObject findInVisibleRect(UiSelector uiSelector) throws InterruptedException {
        return findInVisibleRect(uiSelector, defaultTimeout);
    }
    public static UiObject findInVisibleRect(UiSelector uiSelector, long timeout) throws InterruptedException {
        UiObject result = null;
        UiObject targetObject = null;
        long startTime = System.currentTimeMillis();
        long lastPrintLogTime = 0;

        while (System.currentTimeMillis() - startTime < timeout) {
            UiObject target = new UiObject(uiSelector);
            if (target.exists()) {
                targetObject = target;
                break;
            }
            if (System.currentTimeMillis() - lastPrintLogTime > 2000) {
                Logger.debug("findInVisibleRect(), uiSelector = " + uiSelector + ", find target failed, retry");
                lastPrintLogTime = System.currentTimeMillis();
            }
            Thread.sleep(500);
        }

        Logger.debug("findInVisibleRect()"
                + ", uiSelector=" + uiSelector.toString()
                + ", time = " + (System.currentTimeMillis() - startTime)
                + ", found = " + (null != targetObject));

        // check if in the visible rect
        if (null != targetObject) {
            if (isInScreen(targetObject)) {
                if (isInVisibleRect(targetObject)) {
                    result = targetObject;
                    Logger.debug("findInVisibleRect(), target object found");
                } else {
                    Logger.debug("findInVisibleRect(), target object is out of visible rect");
                }
            } else {
                Logger.debug("findInVisibleRect(), target object is out of screen");
            }
        }

        if (null == result && Constant.BUILD_CONFIG.equals("debug")) {
            // find object failed, save dump
            dumpXml2File("/sdcard/errorDump.xml");
        }

        return result;
    }

    private static boolean checkUiObjectExist(UiObject target) {
        boolean exist = false;
        boolean interrupt = Thread.currentThread().isInterrupted();
        exist = target.exists();
        if (interrupt) {
            Thread.currentThread().interrupt();
        }

        return exist;
    }

    public static ArrayList<UiObject> findAll(UiSelector uiSelector) {
        ArrayList<UiObject> targetObjects = new ArrayList();
        int instanceIndex = 0;

        while (true) {
            UiSelector uiSelectorWithIndex = uiSelector.instance(instanceIndex);
            UiObject target = new UiObject(uiSelectorWithIndex);
            if (target.exists()) {
                targetObjects.add(target);
                instanceIndex++;
            } else {
                break;
            }
        }

        Logger.debug("findAll(), total found objects number = " + targetObjects.size() + ", targetObjects = " + Arrays.toString(targetObjects.toArray()) + ", uiSelector=" + uiSelector.toString());

        return targetObjects;
    }

    public static boolean isInScreenRect(Rect realBounds) {
        boolean result = false;
        if ((realBounds.left < screenSize.getWidth() && realBounds.right > 0) &&
                (realBounds.top < screenSize.getHeight() && realBounds.bottom > 0) &&
                (realBounds.width() > 5 && realBounds.height() > 5)) {
            result = true;
        } else {
            Logger.debug("isInScreenRect(), realBounds is out of screen, realBounds=" + realBounds);
        }
        Logger.debug("isInScreenRect(), result=" + result + ", realBounds=" + realBounds);

        return result;
    }

    public static boolean isInScreen(UiObject target) {
        boolean result = false;
        if (null != target) {
            try {
                Rect realBounds = target.getBounds();
                if ((realBounds.left < screenSize.getWidth() && realBounds.right > 0) &&
                        (realBounds.top < screenSize.getHeight() && realBounds.bottom > 0) &&
                        (realBounds.width() > 10 && realBounds.height() > 10)) {
                    result = true;
                } else {
                    Logger.debug("isInScreen(), target is out of screen, realBounds=" + realBounds);
                }
            } catch (UiObjectNotFoundException e) {
                Logger.debug("isInScreen(), get bounds error, info = " + e.getMessage());
            }
        } else {
            Logger.debug("isInScreen(), target ui object is null");
        }

        return result;
    }
    public static boolean isInVisibleRect(UiObject target) {
        boolean result = false;
        try {
            Rect visibleBounds = target.getVisibleBounds();
            if (visibleBounds.width() > 10 && visibleBounds.height() > 10) {
                result = true;
            } else {
                Logger.debug("isInVisibleRect(), target is out of visible rect, visibleBounds=" + visibleBounds);
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void clickUiObjectCoords(UiObject target) {
        try {
            Rect realBounds = target.getBounds();
            Rect visibleBounds = target.getVisibleBounds();
            Logger.debug("clickUiObjectCoords(), realBounds=" + realBounds + ", visibleBounds=" + visibleBounds);
            int coordX = realBounds.left + realBounds.width() / 2;
            int coordY = realBounds.top + realBounds.height() / 2;
            Logger.debug("clickUiObjectCoords(), coordX = " + coordX + ", coordY = " + coordY);
            clickEx(coordX, coordY);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean findAndClick(UiSelector uiSelector) throws InterruptedException {
        return findAndClick(uiSelector, defaultTimeout);
    }

    public static boolean findAndClick(UiSelector uiSelector, long timeout) throws InterruptedException {
        boolean result = false;
        UiObject target = find(uiSelector, timeout);
        if (null != target) {
            try {
                Logger.debug("findAndClick(), call object click method");
                target.click();
                result = true;

//                if (isInScreen(target)) {
//                    if (isInVisibleRect(target)) {
//                        Logger.debug("findAndClick(), call object click method");
//                        target.click();
//                    } else {
//                        Logger.debug("findAndClick(), click coords");
//                        clickUiObjectCoords(target);
//                    }
//                    result = true;
//                } else {
//                    Logger.debug("findAndClick(), target object is out of screen");
//                }
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        Logger.debug("findAndClick(), result = " + result);
        return result;
    }

    public static boolean clickEx(int coordX, int coordY) {
        boolean result = false;
        if (UiAutomatorBridge.getInstance().getInteractionController().touchDown(coordX, coordY)) {
            SystemClock.sleep(100);
            if (UiAutomatorBridge.getInstance().getInteractionController().touchUp(coordX, coordY)) {
                Logger.debug("clickEx(), click coordination success");
                result = true;
            } else {
                Logger.debug("clickEx(), interactionController.touchUp() failed");
            }
        } else {
            Logger.debug("clickEx(), interactionController.touchDown() failed");
        }
        return result;
    }

    public static Point getUiObjectCenterCoords(UiObject target) {
        Point center = new Point();
        try {
            Rect realBounds = target.getBounds();
            center.x = realBounds.left + realBounds.width() / 2;
            center.y = realBounds.top + realBounds.height() / 2;
        } catch (UiObjectNotFoundException e) {
            Logger.debug("getUiObjectCenterCoords(), get coords failed, info:" + e.getMessage());
            e.printStackTrace();
        }

        return center;
    }

    public static boolean click(Point point) {
        if (null != point) {
            Logger.debug("click(), point=" + point);
            return uiDevice.click(point.x, point.y);
        } else {
            Logger.debug("click(), point is null");
            return false;
        }
    }

    public static boolean click(int coordX, int coordY) {
        return uiDevice.click(coordX, coordY);
    }

    public static boolean click_ScreenCenter() {
        return uiDevice.click(screenSize.getWidth() / 2, screenSize.getHeight() / 2);
    }

    public static boolean click_Top300px_Center() {
        return uiDevice.click(screenSize.getWidth() / 2, 300);
    }

    public static boolean longPress(int coordX, int coordY) {
        return uiDevice.swipe(coordX, coordY, coordX, coordY, 100);
    }

    public static boolean findAndLongPress(UiSelector uiSelector) throws InterruptedException {
        return findAndLongPress(uiSelector, defaultTimeout);
    }

    public static boolean findAndLongPress(UiSelector uiSelector, long timeout) throws InterruptedException {
        boolean result = false;
        UiObject target = find(uiSelector, timeout);
        if (null != target) {
            try {
                Logger.debug("findAndLongPress(), call object click method");
                target.longClick();

//                if (isInScreen(target)) {
//                    if (isInVisibleRect(target)) {
//                        Logger.debug("findAndLongPress(), call object click method");
//                        target.longClick();
//                    } else {
//                        Logger.debug("findAndLongPress(), click coords");
//                        longPressUiObjectCoords(target);
//                    }
//                    result = true;
//                } else {
//                    Logger.debug("findAndLongPress(), target object is out of screen");
//                }
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
        Logger.debug("findAndLongPress(), result = " + result);
        return result;
    }

    public static void longPressUiObjectCoords(UiObject target) {
        try {
            Rect realBounds = target.getBounds();
            Rect visibleBounds = target.getVisibleBounds();
            Logger.debug("longPressUiObjectCoords(), realBounds=" + realBounds + ", visibleBounds=" + visibleBounds);
            int coordX = realBounds.left + realBounds.width() / 2;
            int coordY = realBounds.top + realBounds.height() / 2;
            Logger.debug("longPressUiObjectCoords(), coordX = " + coordX + ", coordY = " + coordY);
            longPress(coordX, coordY);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean swipe(int startX, int startY, int endX, int endY, int steps) {
        return uiDevice.swipe(startX, startY, endX, endY, steps);
    }

    public static boolean pressBack() {
        return uiDevice.pressBack();
    }
    public static boolean pressHome() {
        return uiDevice.pressHome();
    }
    public static boolean pressPower() {
        return uiDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
    }
    public static boolean lightScreenOn() {
        Size screenSize = SysUtil.getScreenSize();
        try {
            if (!uiDevice.isScreenOn()) {
                uiDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                swipeUp600pxFast();
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean lightScreenOff() {
        try {
            if (uiDevice.isScreenOn()) {
                uiDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // swipe up 300px
    public static void swipeUp300pxSlowly() {
        swipeUpSlowly(300);
    }
    public static void swipeUp300pxFast() {
        swipeUpFast(300);
    }
    public static void swipeDown300pxSlowly() {
        swipeDownSlowly(300);
    }
    public static void swipeDown300pxFast() {
        swipeDownFast(300);
    }

    // swipe up 600px
    public static void swipeUp600pxSlowly() {
        swipeUpSlowly(600);
    }
    public static void swipeUp600pxFast() {
        swipeUpFast(600);
    }
    public static void swipeDown600pxSlowly() {
        swipeDownSlowly(600);
    }
    public static void swipeDown600pxFast() {
        swipeDownFast(600);
    }

    // swipe up 800px
    public static void swipeUp800pxSlowly() {
        swipeUpSlowly(800);
    }
    public static void swipeUp800pxFast() {
        swipeUpFast(800);
    }
    public static void swipeDown800pxSlowly() {
        swipeDownSlowly(800);
    }
    public static void swipeDown800pxFast() {
        swipeDownFast(800);
    }
    public static void swipeLeft800pxFast() {
        swipeLeftFast(800);
    }

    // swipe left 1000px
    public static void swipeLeft1000pxFast() {
        swipeLeftFast(1000);
    }
    // swipe up 1000px
    public static void swipeUp1000pxSlowly() {
        swipeUpSlowly(1000);
    }
    public static void swipeUp1000pxFast() {
        swipeUpFast(1000);
    }
    public static void swipeDown1000pxSlowly() {
        swipeDownSlowly(1000);
    }
    public static void swipeDown1000pxFast() {
        swipeDownFast(1000);
    }

    //
    private static void swipeUpSlowly(int distance) {
        if (distance >= screenSize.getHeight()) {
            distance = screenSize.getHeight() - 200;
        }
        swipe(screenSize.getWidth() / 2,
                screenSize.getHeight() / 2 + distance / 2,
                screenSize.getWidth() / 2,
                screenSize.getHeight() / 2 - distance / 2,
                20);
    }
    private static void swipeUpFast(int distance) {
        if (distance >= screenSize.getHeight()) {
            distance = screenSize.getHeight() - 200;
        }
        swipe(screenSize.getWidth() / 2,
                screenSize.getHeight() / 2 + distance / 2,
                screenSize.getWidth() / 2,
                screenSize.getHeight() / 2 - distance / 2,
                10);
    }
    private static void swipeDownSlowly(int distance) {
        if (distance >= screenSize.getHeight()) {
            distance = screenSize.getHeight() - 200;
        }
        swipe(screenSize.getWidth() / 2,
                screenSize.getHeight() / 2 - distance / 2,
                screenSize.getWidth() / 2,
                screenSize.getHeight() / 2 + distance / 2,
                20);
    }
    private static void swipeDownFast(int distance) {
        if (distance >= screenSize.getHeight()) {
            distance = screenSize.getHeight() - 200;
        }
        swipe(screenSize.getWidth() / 2,
                screenSize.getHeight() / 2 - distance / 2,
                screenSize.getWidth() / 2,
                screenSize.getHeight() / 2 + distance / 2,
                10);
    }

    private static void swipeLeftSlowly(int distance) {
        if (distance >= screenSize.getWidth()) {
            distance = screenSize.getWidth() - 100;
        }
        swipe(screenSize.getWidth() / 2 + distance / 2,
                screenSize.getHeight() / 2,
                screenSize.getWidth() / 2 - distance / 2,
                screenSize.getHeight() / 2,
                20);
    }
    private static void swipeLeftFast(int distance) {
        if (distance >= screenSize.getWidth()) {
            distance = screenSize.getWidth() - 100;
        }
        swipe(screenSize.getWidth() / 2 + distance / 2,
                screenSize.getHeight() / 2,
                screenSize.getWidth() / 2 - distance / 2,
                screenSize.getHeight() / 2,
                10);
    }

    public static boolean swipeUpToFindAndClickObject(UiSelector uiSelector, int maxSwipeCount) throws InterruptedException{
        // default max swipe 3 times
        if (swipeUpToFindObject(uiSelector, maxSwipeCount)) {
            return UiDriver.findAndClick(uiSelector);
        } else {
            return false;
        }
    }
    public static boolean swipeUpToFindAndClickObject(UiSelector uiSelector) throws InterruptedException{
        // default max swipe 3 times
        if (swipeUpToFindObject(uiSelector, 3)) {
            return UiDriver.findAndClick(uiSelector);
        } else {
            return false;
        }
    }
    public static boolean swipeDownToFindAndClickObject(UiSelector uiSelector) throws InterruptedException{
        // default max swipe 3 times
        if (swipeDownToFindObject(uiSelector, 3)) {
            return UiDriver.findAndClick(uiSelector);
        } else {
            return false;
        }
    }

    public static boolean swipeUpToFindObject(UiSelector uiSelector) throws InterruptedException{
        // default max swipe 3 times
        return swipeUpToFindObject(uiSelector, 3);
    }
    public static boolean swipeDownToFindObject(UiSelector uiSelector) throws InterruptedException{
        // default max swipe 3 times
        return swipeDownToFindObject(uiSelector, 3);
    }

    public static boolean swipeUpToFindObject(UiSelector uiSelector, int maxSwipeCount) throws InterruptedException{
        int reTryCount = 0;
//        boolean isInScreen = UiDriver.isInScreen(UiDriver.find(uiSelector, 10000));
        UiObject targetObject = UiDriver.find(uiSelector, 10000);

        while (null == targetObject && reTryCount < maxSwipeCount) {
            Logger.debug("UiDriver.swipeUpToFindObject(), not find object, swipe once and retry");
            UiDriver.swipeUp800pxSlowly();
            targetObject = UiDriver.find(uiSelector);
            reTryCount++;
        }

        Logger.debug("UiDriver.swipeUpToFindObject(), targetObject = " + targetObject);
        return null != targetObject;
    }

    public static boolean swipeDownToFindObject(UiSelector uiSelector, int maxSwipeCount) throws InterruptedException{
        int reTryCount = 0;
//        boolean isInScreen = UiDriver.isInScreen(UiDriver.find(uiSelector));
        UiObject targetObject = UiDriver.find(uiSelector, 10000);

        while (null == targetObject && reTryCount < maxSwipeCount) {
            Logger.debug("UiDriver.swipeDownToFindObject(), is not in screen, swipe once and retry");
            UiDriver.swipeDown600pxSlowly();
            targetObject = UiDriver.find(uiSelector);
            reTryCount++;
        }

        Logger.debug("UiDriver.swipeDownToFindObject(), targetObject = " + targetObject);
        return null != targetObject;
    }

    public static void dumpXml2File(String filePath) {
        XMLHierarchy.dumpXml2File(filePath);
    }

    public static String dumpXml2String() {
        return XMLHierarchy.dumpXml2String();
    }
}
