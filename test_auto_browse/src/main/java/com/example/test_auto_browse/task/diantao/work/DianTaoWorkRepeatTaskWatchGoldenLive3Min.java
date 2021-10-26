package com.example.test_auto_browse.task.diantao.work;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

public class DianTaoWorkRepeatTaskWatchGoldenLive3Min extends DianTaoWorkRepeatTask{
    private DianTaoWorkRepeatTaskWatchGoldenLive3Min() {}
    private static DianTaoWorkRepeatTaskWatchGoldenLive3Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkRepeatTaskWatchGoldenLive3Min();
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
        return Constant.DIAN_TAO_WORK_WATCH_GOLDEN_LIVE_3MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        // task only available after 22:30
        Calendar calendar = Calendar.getInstance();
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMin = calendar.get(Calendar.MINUTE);

        if (curHour >= 23 || (curHour == 22 && curMin >= 30)) {
            return getMaxExecCount() -
                    LocalStorageUtil.getCachedTaskExecCount().getDianTaoWorkWatchGoldenLive3MinExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWorkWatchGoldenLive3MinExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // click watch golden live 3min
        if (UiDriver.swipeUpToFindAndClickObject(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_GOLDEN_LIVE_3Min))) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                // enter live window, and wait 3min
                int watchDuration = 1000 * 60 * 3 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, false, true);
                Logger.debug("DianTaoWorkRepeatTaskWatchGoldenLive3Min.autoBrowse(), watchVideoOrLive result = " + result);
            } else {
                // entry live window failed, exit task
                Logger.debug("DianTaoWorkRepeatTaskWatchGoldenLive3Min.autoBrowse(), enter live window failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchGoldenLive3Min.autoBrowse(), click watch golden live 3min failed");
        }

        // return to work page
        backToWorkPage();

        Logger.debug("DianTaoWorkRepeatTaskWatchGoldenLive3Min.autoBrowse(), result=" + result);
        return result;
    }
}
