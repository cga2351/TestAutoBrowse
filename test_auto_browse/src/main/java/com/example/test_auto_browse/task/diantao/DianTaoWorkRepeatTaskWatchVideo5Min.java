package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.CoordsAdapter;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWorkRepeatTaskWatchVideo5Min extends DianTaoWorkRepeatTask{
    private DianTaoWorkRepeatTaskWatchVideo5Min() {}
    private static DianTaoWorkRepeatTaskWatchVideo5Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkRepeatTaskWatchVideo5Min();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 * 5 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WORK_WATCH_VIDEO_5MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWorkWatchVideo5MinExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWorkWatchVideo5MinExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // open get energy task list
        UiDriver.click(CoordsAdapter.getDianTaoGetEnergyCoords());

        // click watch video 5min
        UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_VIDEO_5Min);
        if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                // enter video window, and wait 5min
                int watchDuration = 1000 * 60 * 5 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, true);
                Logger.debug("DianTaoWorkRepeatTaskWatchVideo5Min.autoBrowse(), watchVideoOrLive result = " + result);
            } else {
                // entry live window failed, exit task
                Logger.debug("DianTaoWorkRepeatTaskWatchVideo5Min.autoBrowse(), enter video window failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchVideo5Min.autoBrowse(), click watch video 5min failed");
        }

        // return to work page
        backToWorkPage();

        Logger.debug("DianTaoWorkRepeatTaskWatchVideo5Min.autoBrowse(), result=" + result);
        return result;
    }
}
