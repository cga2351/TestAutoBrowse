package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

public class DianTaoWorkRepeatTaskWatchGoldenLive60Sec extends DianTaoWorkRepeatTask{
    private DianTaoWorkRepeatTaskWatchGoldenLive60Sec() {}
    private static DianTaoWorkRepeatTaskWatchGoldenLive60Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkRepeatTaskWatchGoldenLive60Sec();
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
        return Constant.DIAN_TAO_WORK_WATCH_GOLDEN_LIVE_60SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        // task only available after 18:00
        Calendar calendar = Calendar.getInstance();
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (curHour >= 18) {
            return getMaxExecCount() -
                    LocalStorageUtil.getCachedTaskExecCount().getDianTaoWorkWatchGoldenLive60SecExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWorkWatchGoldenLive60SecExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // click watch golden live 60sec
        if (UiDriver.swipeUpToFindAndClickObject(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_GOLDEN_LIVE_60Sec))) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                // enter live window, and wait 60sec
                int watchDuration = 1000 * 60 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, false, true);
                Logger.debug("DianTaoWorkRepeatTaskWatchGoldenLive60Sec.autoBrowse(), watchVideoOrLive result = " + result);
            } else {
                // entry live window failed, exit task
                Logger.debug("DianTaoWorkRepeatTaskWatchGoldenLive60Sec.autoBrowse(), enter live window failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchGoldenLive60Sec.autoBrowse(), click watch golden live 60sec failed");
        }

        // return to work page
        backToWorkPage();

        Logger.debug("DianTaoWorkRepeatTaskWatchGoldenLive60Sec.autoBrowse(), result=" + result);
        return result;
    }
}