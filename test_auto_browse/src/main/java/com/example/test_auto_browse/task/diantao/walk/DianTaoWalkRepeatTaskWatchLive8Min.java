package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWalkRepeatTaskWatchLive8Min extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskWatchLive8Min() {}
    private static DianTaoWalkRepeatTaskWatchLive8Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskWatchLive8Min();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 * 8 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WALK_WATCH_LIVE_8MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWalkWatchLive8MinExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWalkWatchLive8MinExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch live 8min
        // click get walk steps
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS))) {
            // click watch live 8min
            UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_8Min);
            if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                    // enter live window, and wait 8min
                    int watchDuration = 1000 * 60 * 8 + 1000 * 10;
                    result = watchVideoOrLive(watchDuration, false, true);
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive8Min.autoBrowse(), watch result = " + result);
                } else {
                    // entry live window failed, exit task
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive8Min.autoBrowse(), enter live window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchLive8Min.autoBrowse(), click watch live 8min failed");
            }

            // press back
            UiDriver.pressBack();
            Thread.sleep(2000);
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchLive8Min.autoBrowse(), click get walk steps failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchLive8Min.autoBrowse(), result=" + result);
        return result;
    }

}
