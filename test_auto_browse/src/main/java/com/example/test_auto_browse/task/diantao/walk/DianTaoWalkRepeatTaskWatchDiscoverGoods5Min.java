package com.example.test_auto_browse.task.diantao.walk;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoWalkRepeatTaskWatchDiscoverGoods5Min extends DianTaoWalkRepeatTask {
    private DianTaoWalkRepeatTaskWatchDiscoverGoods5Min() {}
    private static DianTaoWalkRepeatTaskWatchDiscoverGoods5Min instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoWalkRepeatTaskWatchDiscoverGoods5Min();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
//        return 1000 * 30 + 1000 * 30;
        return 1000 * 60 * 5 * 2 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_WALK_WATCH_DISCOVER_GOODS_5MIN_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDianTaoWalkWatchDiscoverGoods5MinExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoWalkWatchDiscoverGoods5MinExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // watch discover goods 5min
        // click get walk steps
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_WALK_STEPS))) {
            // click watch discover goods 5min
            UiSelector selector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_DISCOVER_GOODS_5Min);
            if (UiDriver.swipeUpToFindObject(selector) && UiDriver.findAndClick(selector)) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_DIAN_TAO_AFTER_S_COMPLETE))) {
                    // enter video window, and wait 5min
                    int watchDuration = 1000 * 60 * 5 + 1000 * 10;
                    result = watchVideoOrLive(watchDuration, true, false);
                    Logger.debug("DianTaoWalkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), watch result = " + result);
                } else {
                    // entry video window failed, exit task
                    Logger.debug("DianTaoWalkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), enter video window failed");
                }
            } else {
                Logger.debug("DianTaoWalkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), click watch discover goods 5min failed");
            }

            // press back
            UiDriver.pressBack();
            Thread.sleep(2000);
        } else {
            Logger.debug("DianTaoWalkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), click get walk steps failed");
        }

        Logger.debug("DianTaoWalkRepeatTaskWatchDiscoverGoods5Min.autoBrowse(), result=" + result);
        return result;
    }
}

