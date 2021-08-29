package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWalkRepeatTaskWatchVideo60Sec extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskWatchVideo60Sec() {}
    private static DianTaoWalkRepeatTaskWatchVideo60Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskWatchVideo60Sec();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WALK_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWalkWatchVideo60SecExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWalkWatchVideo60SecExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch video 60s
        // click get walk steps
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS))) {
            // click watch video 60s
            UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_VIDEO_60Sec);
            if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                    // enter video window, and wait 60sec
                    int watchDuration = 1000 * 60 + 1000 * 10;
                    result = watchVideoOrLive(watchDuration, true);
                    Logger.debug("DianTaoWalkRepeatTaskWatchVideo60Sec.autoBrowse(), watch result = " + result);
                } else {
                    // entry video window failed, exit task
                    Logger.debug("DianTaoWalkRepeatTaskWatchVideo60Sec.autoBrowse(), enter video window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchVideo60Sec.autoBrowse(), click watch video 60s failed");
            }

            // press back
            UiDriver.pressBack();
            Thread.sleep(2000);
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchVideo60Sec.autoBrowse(), click get walk steps failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchVideo60Sec.autoBrowse(), result=" + result);
        return result;
    }

}
