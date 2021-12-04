package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWalkRepeatTaskWatchVideo30Sec extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskWatchVideo30Sec() {}
    private static DianTaoWalkRepeatTaskWatchVideo30Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskWatchVideo30Sec();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
//        return 1000 * 30 + 1000 * 30;
        return 1000 * 60 * 10 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WALK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWalkWatchVideo30SecExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWalkWatchVideo30SecExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch video 30 sec
        // click get walk steps
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS))) {
            // click watch video 5 min
            UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_VIDEO_30Sec);
            if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), Constant.WAIT_TIME_10_SEC)) {
                    // enter video window, and wait 30s
                    int watchDuration = 1000 * 30 + 1000 * 10;
                    result = watchVideoOrLive(watchDuration, true, false);
                    Logger.debug("DianTaoWalkRepeatTaskWatchVideo30Sec.autoBrowse(), watch result = " + result);
                } else {
                    // entry video window failed, exit task
                    Logger.debug("DianTaoWalkRepeatTaskWatchVideo30Sec.autoBrowse(), enter video window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchVideo30Sec.autoBrowse(), click watch video 30s failed");
            }

            // press back
            UiDriver.pressBack();
            Thread.sleep(2000);
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchVideo30Sec.autoBrowse(), click get walk steps failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchVideo30Sec.autoBrowse(), result=" + result);
        return result;
    }
}

