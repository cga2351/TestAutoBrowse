package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.task.diantao.work.DianTaoWorkRepeatTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

public class DianTaoWalkRepeatTaskWatchGoldenLive30Sec extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskWatchGoldenLive30Sec() {}
    private static DianTaoWalkRepeatTaskWatchGoldenLive30Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskWatchGoldenLive30Sec();
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
        return Constant.DIAN_TAO_WALK_WATCH_GOLDEN_LIVE_30SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        // task only available after 18:00
        Calendar calendar = Calendar.getInstance();
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (curHour >= 18) {
            return getMaxExecCount() -
                    LocalStorageUtil.getCachedTaskExecCount().getDianTaoWalkWatchGoldenLive30SecExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWalkWatchGoldenLive30SecExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch live 30s
        // click get walk steps
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS))) {
            // click watch live 30s
            UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_GOLDEN_LIVE_30Sec);
            if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), Constant.WAIT_TIME_10_SEC)) {
                    // enter live window, and wait 30sec
                    int watchDuration = 1000 * 30 + 1000 * 10;
                    result = watchVideoOrLive(watchDuration, false, true);
                    Logger.debug("DianTaoWalkRepeatTaskWatchGoldenLive30Sec.autoBrowse(), watch result = " + result);
                } else {
                    // entry live window failed, exit task
                    Logger.debug("DianTaoWalkRepeatTaskWatchGoldenLive30Sec.autoBrowse(), enter live window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchGoldenLive30Sec.autoBrowse(), click watch live golden live 30sec failed");
            }

            // press back
            UiDriver.pressBack();
            Thread.sleep(2000);
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchGoldenLive30Sec.autoBrowse(), click get walk steps failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchGoldenLive30Sec.autoBrowse(), result=" + result);
        return result;
    }
}
