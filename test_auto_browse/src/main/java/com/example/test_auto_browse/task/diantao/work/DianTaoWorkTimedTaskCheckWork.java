package com.example.test_auto_browse.task.diantao.work;

import android.graphics.Point;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
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

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // wait page load end and get energy
        getTimedEnergy();

        // click sign to get energy
        UiDriver.click(CoordsAdapter.getDianTaoWorkSign());

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
            result = true;
        } else {
            // select the cook work
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_COOK_WORK))) {
                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_START_WORK))) {
                    Thread.sleep(2000);
                    Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), start work success");
                    result = true;
                } else if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_DIAN_TAO_START_NO_AVAILABLE_ENERGY))) {
                    Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), no available energy");
                } else {
                    Logger.debug("DianTaoWorkTimedTaskCheckWork.startNextWork(), start work failed");
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
    }
}
