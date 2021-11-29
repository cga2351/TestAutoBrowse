package com.example.test_auto_browse.task.diantao.work;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWorkRepeatTaskWatchVideo3Min extends DianTaoWorkRepeatTask{
    private DianTaoWorkRepeatTaskWatchVideo3Min() {}
    private static DianTaoWorkRepeatTaskWatchVideo3Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkRepeatTaskWatchVideo3Min();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
//        return 1000 * 60 * 5 + 1000 * 30;
        return 1000 * 60 * 3 * 3 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WORK_WATCH_VIDEO_3MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWorkWatchVideo3MinExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWorkWatchVideo3MinExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // click watch video 3min
        if (UiDriver.swipeUpToFindAndClickObject(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_VIDEO_3Min))) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                // enter video window, and wait 3min
                int watchDuration = 1000 * 60 * 3 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, true, false);
                Logger.debug("DianTaoWorkRepeatTaskWatchVideo3Min.autoBrowse(), watchVideoOrLive result = " + result);
            } else {
                // entry live window failed, exit task
                Logger.debug("DianTaoWorkRepeatTaskWatchVideo3Min.autoBrowse(), enter video window failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchVideo3Min.autoBrowse(), click watch video 3min failed");
        }

        // return to work page
        backToWorkPage();

        Logger.debug("DianTaoWorkRepeatTaskWatchVideo3Min.autoBrowse(), result=" + result);
        return result;
    }
}
