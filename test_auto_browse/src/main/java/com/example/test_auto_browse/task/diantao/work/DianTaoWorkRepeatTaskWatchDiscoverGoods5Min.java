package com.example.test_auto_browse.task.diantao.work;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWorkRepeatTaskWatchDiscoverGoods5Min extends DianTaoWorkRepeatTask{
    private DianTaoWorkRepeatTaskWatchDiscoverGoods5Min() {}
    private static DianTaoWorkRepeatTaskWatchDiscoverGoods5Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWorkRepeatTaskWatchDiscoverGoods5Min();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
//        return 1000 * 60 * 5 + 1000 * 30;
        return 1000 * 60 * 5 * 2 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WORK_WATCH_DISCOVER_GOODS_5MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWorkWatchDiscoverGoods5MinExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWorkWatchDiscoverGoods5MinExecCount());
    }

    @Override
    protected boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // click watch discover goods 5min
        if (UiDriver.swipeUpToFindAndClickObject(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_DISCOVER_GOODS_5Min))) {
            if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE), Constant.WAIT_TIME_10_SEC)) {
                // enter video window, and wait 5min
                int watchDuration = 1000 * 60 * 5 + 1000 * 10;
                result = watchVideoOrLive(watchDuration, true, false);
                Logger.debug("DianTaoWorkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), watchVideoOrLive result = " + result);
            } else {
                // entry live window failed, exit task
                Logger.debug("DianTaoWorkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), enter video window failed");
            }
        } else {
            Logger.debug("DianTaoWorkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), click watch discover goods 5min failed");
        }

        // return to work page
        backToWorkPage();

        Logger.debug("DianTaoWorkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), result=" + result);
        return result;
    }
}
