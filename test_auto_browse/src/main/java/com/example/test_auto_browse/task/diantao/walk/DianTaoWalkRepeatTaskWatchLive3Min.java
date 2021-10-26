package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWalkRepeatTaskWatchLive3Min extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskWatchLive3Min() {}
    private static DianTaoWalkRepeatTaskWatchLive3Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskWatchLive3Min();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 * 3 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WALK_WATCH_LIVE_3MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWalkWatchLive3MinExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWalkWatchLive3MinExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch live 3min
        // click get walk steps
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS))) {
            // click watch live 3min
            UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_3Min);
            if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                    // enter live window, and wait 3min
                    int watchDuration = 1000 * 60 * 3 + 1000 * 10;
                    result = watchVideoOrLive(watchDuration, false, true);
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive3Min.autoBrowse(), watch result = " + result);
                } else {
                    // entry live window failed, exit task
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive3Min.autoBrowse(), enter live window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchLive3Min.autoBrowse(), click watch live 3min failed");
            }

            // press back
            UiDriver.pressBack();
            Thread.sleep(2000);
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchLive3Min.autoBrowse(), click get walk steps failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchLive3Min.autoBrowse(), result=" + result);
        return result;
    }

}
