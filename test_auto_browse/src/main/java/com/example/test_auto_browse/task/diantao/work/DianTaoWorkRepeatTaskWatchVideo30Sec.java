package com.example.test_auto_browse.task.diantao.work;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWorkRepeatTaskWatchVideo30Sec extends DianTaoWorkRepeatTask{
    private DianTaoWorkRepeatTaskWatchVideo30Sec() {}
    private static DianTaoWorkRepeatTaskWatchVideo30Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkRepeatTaskWatchVideo30Sec();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 30 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WORK_WATCH_VIDEO_30SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWorkWatchVideo30SecExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWorkWatchVideo30SecExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // click watch video 3min
        if (UiDriver.swipeUpToFindAndClickObject(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_VIDEO_30Sec))) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                // enter video window, and wait 30s
                int watchDuration = 1000 * 30 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, true, false);
                Logger.debug("DianTaoWorkRepeatTaskWatchVideo30Sec.autoBrowse(), watchVideoOrLive result = " + result);
            } else {
                // entry live window failed, exit task
                Logger.debug("DianTaoWorkRepeatTaskWatchVideo30Sec.autoBrowse(), enter video window failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchVideo30Sec.autoBrowse(), click watch video 30s failed");
        }

        // return to work page
        backToWorkPage();

        Logger.debug("DianTaoWorkRepeatTaskWatchVideo30Sec.autoBrowse(), result=" + result);
        return result;
    }
}
