package com.example.test_auto_browse.task.qiyi;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.BrowseBaseTask;
import com.example.test_auto_browse.utils.Logger;
import com.example.test_auto_browse.utils.SysUtil;

public abstract class QiYiBaseTask extends BrowseBaseTask {
    private static long lastQiYiDailyTaskCheckTime = 0;

    @Override
    public long getLastDailyTaskCheckTime() {
        return lastQiYiDailyTaskCheckTime;
    }

    @Override
    public void setLastCheckTime(long time) {
        lastQiYiDailyTaskCheckTime = time;
    }

    @Override
    public String getTargetAppPackageName() { return Constant.PKG_NAME_QI_YI; }

    @Override
    public void exeDailyTasks() throws InterruptedException {

    }

    @Override
    public boolean initTask() throws InterruptedException {
        Logger.debug("QiYiBaseTask.initTask(), entry");
//        taskPackageName = Constant.PKG_NAME_QI_YI;

        boolean result = false;
        if (super.initTask()) {
            // jump ads and close upgrade popup
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QI_YI_JUMP_ADS))) {
                Logger.debug("QiYiBaseTask.initTask(), jump ads success");
            }
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QI_YI_DO_NOT_UPGRADE))) {
                Logger.debug("QiYiBaseTask.initTask(), close upgrade popup, 1");
            }

            // switch to the gold tab
            if (null != UiDriver.find(new UiSelector().text(Constant.STR_QI_YI_GOLD_TAB))) {
                // wait home page load end
                Thread.sleep(7000);

                UiDriver.findAndClick(new UiSelector().text(Constant.STR_QI_YI_GOLD_TAB));

                if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QI_YI_DO_NOT_UPGRADE))) {
                    Logger.debug("QiYiBaseTask.initTask(), close upgrade popup, 2");
                }

                // close the sign popup
                if (null != UiDriver.find(new UiSelector().textStartsWith(Constant.STR_QI_YI_SIGN_SUCCESS))) {
                    UiDriver.findAndClick(new UiSelector().clickable(true).instance(1));
                }

                if (null != UiDriver.find(new UiSelector().description(Constant.STR_QI_YI_DAILY_TASK))) {
                    result = true;
                }
            } else {
                Logger.debug("QiYiBaseTask.initTask(), switch to gold tab page failed");
            }
        } else {
            Logger.debug("QiYiBaseTask.initTask(), super.initTask() failed");
        }

        return result;
    }

    @Override
    public void endTask() throws InterruptedException {
        Logger.debug("QiYiBaseTask.endTask(), entry, task = " + getClass().getSimpleName());

        // kill app
        SysUtil.killApp(Constant.PKG_NAME_QI_YI);
    }

    protected boolean watchVideoOrLive(int watchDuration) throws InterruptedException {
        boolean result;
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < watchDuration) {
            Thread.sleep(8000);
            UiDriver.swipeUp800pxFast();
        }

        // check count down end
        result = waitWatchVideoOrLiveCountDownEnd();
        if (!result) {
            Logger.debug("QiYiBaseTask.watchVideoOrLive(), watch video or live for " + (watchDuration / (60.0f * 1000)) +  "min failed");
        }
        return result;
    }

    protected boolean waitWatchVideoOrLiveCountDownEnd() throws InterruptedException {
        boolean result = false;

        // wait count down end, max wait 2 min
        long startWaitTime = System.currentTimeMillis();
        while (null != UiDriver.find(new UiSelector().textContains(Constant.STR_QI_YI_AFTER_S_COMPLETE), 3000) &&
                (System.currentTimeMillis() - startWaitTime) < 1000 * 60 * 2) {
            UiDriver.swipeUp600pxFast();
            Thread.sleep(1000 * 10);
        }

        if (null == UiDriver.find(new UiSelector().textContains(Constant.STR_QI_YI_AFTER_S_COMPLETE), 3000)) {
            result = true;
        }

        return result;
    }
}
