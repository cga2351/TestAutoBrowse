package com.example.test_auto_browse.task.diantao.walk;

import android.graphics.Point;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWalkRepeatTaskStepsToGold extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskStepsToGold() {}
    private static DianTaoWalkRepeatTaskStepsToGold instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskStepsToGold();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 * 5;
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;

        if (super.initTask()) {
//            UiDriver.saveDebugScreenshotWithDelay("StepsToGoldFailure_beforeClickDepart_" + getClass().getSimpleName(), 2000);
            // convert all available steps to gold
            // click depart first, if no available steps, click
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_DEPART), Constant.WAIT_TIME_15_SEC)) {
//                UiDriver.saveDebugScreenshotWithDelay("StepsToGoldFailure_beforeWait5s_" + getClass().getSimpleName(), 1000);
                // wait 7s and then press back first, and re-entry the walk page
                Thread.sleep(7000);

                // save debug screen
//                UiDriver.saveDebugScreenshotWithDelay("StepsToGoldFailure_beforePressBack_" + getClass().getSimpleName(), 2000);
                UiDriver.pressBack();
//                UiDriver.saveDebugScreenshotWithDelay("StepsToGoldFailure_afterPressBack_" + getClass().getSimpleName(), 2000);

                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_WALK_GET_GOLD))) {
                    Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), re-enter walk page success");
                } else {
                    Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), re-enter walk page failed");
                }
//                UiDriver.saveDebugScreenshotWithDelay("StepsToGoldFailure_afterReEnterWalkPage_" + getClass().getSimpleName(), 2000);
            } else {
                Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), click depart failed");
            }
            result = true;
        } else {
            Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), super.initTask() failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // get gold and watch ads video
//        UiObject getGold = UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_GET_STEPS_GOLD));
        UiObject getGold = UiDriver.find(new UiSelector().textMatches("^(\\d{1,3}元宝)\\d{1,5}[步]$")); // 匹配 1~3位数字+元宝"开头+1~5位数字+"步" 结尾字符。类似 "188元宝12500步"
        while (null != getGold) {
            Thread.sleep(1000 * 2);

            // get gold button may be covered by icon, click coords a little up of the center
            Point center = UiDriver.getUiObjectCenterCoords(getGold);
            UiDriver.click(center.x, center.y - 50);

            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_GET_STEPS_GOLD_FAILED))) {
                Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), no gold to get");
                break;
            } else {
                // if has live ads, watch live
                if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_WATCH_LIVE_TO_GET_MORE))) {
                    // watch live, and wait 30s
                    Thread.sleep(1000 * 35);

                    if (null == UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), 3000)) {
                        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), watch live ads success");
                    }
                    UiDriver.pressBack();
                } else {
                    if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_I_KNOW))) {
                        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), no ads to get more, click I know button");
                    } else {
                        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), failed, unknown error");
                    }
                }

                result = true;

                // get next gold
                getGold = UiDriver.find(new UiSelector().textMatches("^(\\d{1,3}元宝)\\d{1,5}[步]$"));  // 匹配 1~3位数字+元宝"开头+1~5位数字+"步" 结尾字符。类似 "18元宝12500步"
                Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), try to get next gold, getGold = " + getGold);
//                UiDriver.saveDebugScreenshot("StepsToGoldGetNextGold_" + getClass().getSimpleName());
            }
        }
//        UiDriver.saveDebugScreenshot("StepsToGoldEnd_" + getClass().getSimpleName());

        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), result=" + result);
        return result;
    }

//    @Override
//    public boolean autoBrowse() throws InterruptedException {
//        boolean result = false;
//
//        // get gold and watch ads video
//        UiObject getGold = UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_GET_STEPS_GOLD));
//        while (null != getGold) {
//            Thread.sleep(1000 * 2);
//
//            // get gold button may be covered by icon, click coords a little up of the center
//            Point center = UiDriver.getUiObjectCenterCoords(getGold);
//            UiDriver.click(center.x, center.y - 50);
//
//            if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_GET_ENERGY_DRINK_FAILED))) {
//                Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), no gold to get");
//                break;
//            } else {
//                // if has live ads, watch live
//                if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_WATCH_LIVE_TO_GET_MORE))) {
//                    // watch live, and wait 30s
//                    Thread.sleep(1000 * 35);
//
//                    if (null == UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), 3000)) {
//                        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), watch live ads success");
//                    }
//                    UiDriver.pressBack();
//                } else {
//                    if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_I_KNOW))) {
//                        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), no ads to get more, click I know button");
//                    } else {
//                        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), failed, unknown error");
//                    }
//                }
//
//                result = true;
//
//                // get next gold
//                getGold = UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_GET_STEPS_GOLD));
//                Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), try to get next gold, getGold = " + getGold);
////                UiDriver.saveDebugScreenshot("StepsToGoldGetNextGold_" + getClass().getSimpleName());
//            }
//        }
////        UiDriver.saveDebugScreenshot("StepsToGoldEnd_" + getClass().getSimpleName());
//
//        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), result=" + result);
//        return result;
//    }

}
