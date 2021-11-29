package com.example.test_auto_browse.task.diantao.work;

import android.graphics.Point;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.task.TimedTaskAvailableChecker;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWorkTimedTaskCheckWork extends DianTaoWorkRepeatTask {
    private DianTaoWorkTimedTaskCheckWork() {}
    private static DianTaoWorkTimedTaskCheckWork instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkTimedTaskCheckWork();
                }
            }
        }
        return instance;
    }

    // for timed task
    TimedTaskAvailableChecker timedTaskChecker = new TimedTaskAvailableChecker() {
        @Override
        protected int getExecInterval() {
            return 1000 * 60 * 60 * 2;
        }
    };

    @Override
    protected int getLeftExecCount() {
        if (timedTaskChecker.isTimedTaskAvailable()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // wait page load end and get energy
        getTimedEnergy();

        // get sign energy
        if (null == UiDriver.find(new UiSelector().textStartsWith("连续签到 6/7 天"))) {
            UiDriver.click(CoordsAdapter.getDianTaoWorkSign());
        }

        // get work gold
        UiObject getWorkGold = UiDriver.find(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_GET_WORK_GOLD));
        if (null != getWorkGold) {
            // get work gold button can't be clicked, click coords a little up of the center
            Point center = UiDriver.getUiObjectCenterCoords(getWorkGold);
            UiDriver.click(center.x, center.y - 100);

            // watch ads to get more
            watchAdsToGetMore();

            Logger.debug("DianTaoWorkTimedTaskCheckWork.autoBrowse(), get gold success");
        } else {
            Logger.debug("DianTaoWorkTimedTaskCheckWork.autoBrowse(), no work gold");
        }

        // start next work
        result = startNextWork();

        if (result) {
            timedTaskChecker.setLastSuccessTime(System.currentTimeMillis());
        }

        Logger.debug("DianTaoWorkTimedTaskCheckWork.autoBrowse(), result=" + result);
        return result;
    }

    private void watchAdsToGetMore() throws InterruptedException {
        if (UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_WATCH_LIVE))) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                //
                Logger.debug("DianTaoWorkTimedTaskCheckWork.watchAdsToGetMore(), watch ads to get more");
                boolean checkResult = watchVideoOrLive(1000 * 60, false, true);
                Logger.debug("DianTaoWorkTimedTaskCheckWork.watchAdsToGetMore(), wait ads end result =" + checkResult);

                // close live ads window
                UiDriver.pressBack();
            } else {
                Logger.debug("DianTaoWorkTimedTaskCheckWork.watchAdsToGetMore(), enter watch ads window failed");
            }
        } else {
            if (UiDriver.findAndClick(new UiSelector().textContains(Constant.STR_DIAN_TAO_I_KNOW))) {
                Logger.debug("DianTaoWorkTimedTaskCheckWork.watchAdsToGetMore(), no ads to get more");
            } else {
                Logger.debug("DianTaoWorkTimedTaskCheckWork.watchAdsToGetMore(), failed, unknown error");
            }
        }

        Thread.sleep(3000);
    }

    private boolean startNextWork() throws InterruptedException {
        boolean result = false;
        UiDriver.click(CoordsAdapter.getDianTaoWorkToGetGoldCoords());

        if (UiDriver.findAndClick(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_I_AM_WORKING))) {
            Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), work not completed");
        } else {
            // select the cook work
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_COOK_WORK))) {
                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_START_WORK))) {
                    Thread.sleep(2000);
                    if (null != UiDriver.find(new UiSelector().textStartsWith("01:59:"))) {
                        Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), start work success");
                        result = true;
                    } else {
                        Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), start work failed");
                    }
                } else if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_START_NO_AVAILABLE_ENERGY))) {
                    Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), no available energy");
                } else {
                    Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), find start work button failed");
                }
            } else {
                Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), select cook work failed");
            }
        }

        return result;
    }

    private void getTimedEnergy() throws InterruptedException {
        UiSelector uiSelector = new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_START_GET_ENERGY);

        if (null != UiDriver.find(uiSelector)) {
            Thread.sleep(5000);
            UiDriver.findAndClick(uiSelector);
        }

        // dismiss the more task popup and re-enter the work page
        if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_WORK_MORE_TASKS))) {
            UiDriver.pressBack();
            UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_WORK_TO_GET_GOLD));
        }
    }
}
