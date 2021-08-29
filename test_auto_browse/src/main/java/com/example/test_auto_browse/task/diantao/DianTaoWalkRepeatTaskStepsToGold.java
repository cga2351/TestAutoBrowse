package com.example.test_auto_browse.task.diantao;

import android.graphics.Point;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.DateUtil;
import com.example.test_auto_browse.utils.Logger;

import java.io.File;

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
            // convert all available steps to gold
            // click depart first, if no available steps, click
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_DEPART), Constant.WAIT_TIME_15_SEC)) {
                ///////////////////////////////////////////////////////////////////////////////////
//                // depart success, get all gold
//                if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_FINISH_TASK_TO_GET_STEPS))) {
//                    Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), no available steps");
//                    UiDriver.click_Top300px_Center();
//                }
//
//                // dismiss the popup
//                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_CHECK_MORE_TASKS))) {
//                    Thread.sleep(2000);
//                    UiDriver.click_Top300px_Center();
//                    Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), has get more steps prompt popup");
//                } else {
//                    Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), no get more steps prompt popup");
//                }
//
//                UiDriver.dumpXml2File(Constant.SD_PATH_APP_ROOT + "StepsToGoldFailure.xml");
//                // get screenshot and save pic
//                String savePath = Constant.SD_PATH_FAILED_SCREENSHOTS
//                        + DateUtil.getFormatDate(DateUtil.DATA_FORMAT_yyyy_MM_dd_hh_mm_ss_UNDERLINE, System.currentTimeMillis())
//                        + "_StepsToGoldFailure1_" + getClass().getSimpleName()
//                        + ".jpg";
//                UiDriver.saveScreenshot(new File(savePath));

                /////////////////////////////////////////////////////////////////////////////////
                // press back first, and re-entry the walk page
                UiDriver.saveDebugScreenshot("StepsToGoldFailure_beforeWait5s_" + getClass().getSimpleName());
                Thread.sleep(5000);
                UiDriver.saveDebugScreenshot("StepsToGoldFailure_beforePressBack_" + getClass().getSimpleName());
                UiDriver.pressBack();
                UiDriver.saveDebugScreenshot("StepsToGoldFailure_afterPressBack_" + getClass().getSimpleName());

                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_WALK_GET_GOLD))) {
                    Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), re-enter walk page success");
                } else {
                    Logger.debug("DianTaoWalkRepeatTaskStepsToGold.initTask(), re-enter walk page failed");
                }
                UiDriver.saveDebugScreenshot("StepsToGoldFailure_afterReEnterWalkPage_" + getClass().getSimpleName());

                /////////////////////////////////////////////////////////////////////////////////



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
        UiObject getGold = UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_GET_STEPS_GOLD));
        while (null != getGold) {
            Thread.sleep(1000 * 2);

            // get gold button may be covered by icon, click coords a little up of the center
            Point center = UiDriver.getUiObjectCenterCoords(getGold);
            UiDriver.click(center.x, center.y - 50);

            if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_GET_ENERGY_DRINK_FAILED))) {
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
                }

                result = true;

                // get next gold
                getGold = UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_GET_STEPS_GOLD));
                Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), try to get next gold, getGold = " + getGold);
                UiDriver.saveDebugScreenshot("StepsToGoldGetNextGold_" + getClass().getSimpleName());
            }
        }
        UiDriver.saveDebugScreenshot("StepsToGoldEnd_" + getClass().getSimpleName());

        Logger.debug("DianTaoWalkRepeatTaskStepsToGold.autoBrowse(), result=" + result);
        return result;
    }

}
