package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

public class DianTaoWalkRepeatTaskWatchLive10Sec extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskWatchLive10Sec() {}
    private static DianTaoWalkRepeatTaskWatchLive10Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskWatchLive10Sec();
                }
            }
        }
        return instance;
    }



    @Override
    public int waitTaskEndMaxTime() {
//        return 1000 * 30 + 1000 * 30;
        return 1000 * 60 * 3 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WALK_WATCH_LIVE_10SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWalkWatchLive10SecExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWalkWatchLive10SecExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch live 10s
        // click get walk steps
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS))) {
            // 先完成一天只有一次的单次任务
            checkOneTimeTask();

            // click watch live 10s
            UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_10Sec);
            if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                    // enter live window, and wait 10s
                    int watchDuration = 1000 * 10 + 1000 * 10;
                    result = watchVideoOrLive(watchDuration, false, true);
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive10Sec.autoBrowse(), watch result = " + result);
                } else {
                    // entry live window failed, exit task
                    Logger.debug("DianTaoWalkRepeatTaskWatchLive10Sec.autoBrowse(), enter live window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchLive10Sec.autoBrowse(), click watch live 10s failed");
            }

            // press back
            UiDriver.pressBack();
            Thread.sleep(2000);
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchLive10Sec.autoBrowse(), click get walk steps failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchLive10Sec.autoBrowse(), result=" + result);
        return result;
    }
}
