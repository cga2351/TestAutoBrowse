package com.example.test_auto_browse.task.diantao.lottery;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoLotteryRepeatTaskWatchLive3Min extends DianTaoLotteryRepeatTask {
    private DianTaoLotteryRepeatTaskWatchLive3Min() {}
    private static DianTaoLotteryRepeatTaskWatchLive3Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoLotteryRepeatTaskWatchLive3Min();
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
        return Constant.DIAN_TAO_LOTTERY_WATCH_LIVE_3MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoLotteryWatchLive3MinExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoLotteryWatchLive3MinExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch live 3 min
        UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_3Min);
        if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                // enter live window, and wait 3 min
                int watchDuration = 1000 * 60 * 3 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, false, true);
                Logger.debug("DianTaoLotteryRepeatTaskWatchLive3Min.autoBrowse(), watch result = " + result);
            } else {
                // entry video window failed, exit task
                Logger.debug("DianTaoLotteryRepeatTaskWatchLive3Min.autoBrowse(), enter video window failed");
            }
        } else {
            Logger.debug("DianTaoLotteryRepeatTaskWatchLive3Min.autoBrowse(), click watch live 3min failed");
        }

        // press back
        UiDriver.pressBack();
        Thread.sleep(2000);

        Logger.debug("DianTaoLotteryRepeatTaskWatchLive3Min.autoBrowse(), result=" + result);
        return result;
    }
}
