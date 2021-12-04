package com.example.test_auto_browse.task.diantao.lottery;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoLotteryRepeatTaskWatchVideo60Sec extends DianTaoLotteryRepeatTask {
    private DianTaoLotteryRepeatTaskWatchVideo60Sec() {}
    private static DianTaoLotteryRepeatTaskWatchVideo60Sec instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoLotteryRepeatTaskWatchVideo60Sec();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
//        return 1000 * 60 + 1000 * 30;
        return 1000 * 60 * 10 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_LOTTERY_WATCH_VIDEO_60SEC_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoLotteryWatchVideo60SecExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoLotteryWatchVideo60SecExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch video 60 sec
        UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_VIDEO_60Sec);
        if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), Constant.WAIT_TIME_10_SEC)) {
                // enter video window, and wait 60s
                int watchDuration = 1000 * 60 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, true, false);
                Logger.debug("DianTaoLotteryRepeatTaskWatchVideo60Sec.autoBrowse(), watch result = " + result);
            } else {
                // entry video window failed, exit task
                Logger.debug("DianTaoLotteryRepeatTaskWatchVideo60Sec.autoBrowse(), enter video window failed");
            }
        } else {
            Logger.debug("DianTaoLotteryRepeatTaskWatchVideo60Sec.autoBrowse(), click watch video 60s failed");
        }

        // press back
        UiDriver.pressBack();
        Thread.sleep(2000);

        Logger.debug("DianTaoLotteryRepeatTaskWatchVideo60Sec.autoBrowse(), result=" + result);
        return result;
    }
}
