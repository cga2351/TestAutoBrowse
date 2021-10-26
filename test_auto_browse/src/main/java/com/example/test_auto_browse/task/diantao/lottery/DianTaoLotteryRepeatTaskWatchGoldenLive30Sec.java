package com.example.test_auto_browse.task.diantao.lottery;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

import java.util.Calendar;

public class DianTaoLotteryRepeatTaskWatchGoldenLive30Sec extends DianTaoLotteryRepeatTask {
    private DianTaoLotteryRepeatTaskWatchGoldenLive30Sec() {}
    private static DianTaoLotteryRepeatTaskWatchGoldenLive30Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoLotteryRepeatTaskWatchGoldenLive30Sec();
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
        return Constant.DIAN_TAO_LOTTERY_WATCH_GOLDEN_LIVE_30SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        // task only available after 18:00
        Calendar calendar = Calendar.getInstance();
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);

        if (curHour >= 18) {
            return getMaxExecCount() -
                    LocalStorageUtil.getCachedTaskExecCount().getDianTaoLotteryWatchGoldenLive30SecExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoLotteryWatchGoldenLive30SecExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch live 30 sec
        UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_GOLDEN_LIVE_30Sec);
        if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                // enter live window, and wait 30s
                int watchDuration = 1000 * 30 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, false, true);
                Logger.debug("DianTaoLotteryRepeatTaskWatchGoldenLive30Sec.autoBrowse(), watch result = " + result);
            } else {
                // entry video window failed, exit task
                Logger.debug("DianTaoLotteryRepeatTaskWatchGoldenLive30Sec.autoBrowse(), enter video window failed");
            }
        } else {
            Logger.debug("DianTaoLotteryRepeatTaskWatchGoldenLive30Sec.autoBrowse(), click watch live 30s failed");
        }

        // press back
        UiDriver.pressBack();
        Thread.sleep(2000);

        Logger.debug("DianTaoLotteryRepeatTaskWatchGoldenLive30Sec.autoBrowse(), result=" + result);
        return result;
    }
}
