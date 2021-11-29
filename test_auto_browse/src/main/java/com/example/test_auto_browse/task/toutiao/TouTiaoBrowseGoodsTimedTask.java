package com.example.test_auto_browse.task.toutiao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.task.TimedTaskAvailableChecker;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class TouTiaoBrowseGoodsTimedTask extends TouTiaoBaseTask {
    private TouTiaoBrowseGoodsTimedTask() {}
    private static TouTiaoBrowseGoodsTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new TouTiaoBrowseGoodsTimedTask();
                }
            }
        }
        return instance;
    }

    // for timed task
    TimedTaskAvailableChecker timedTaskChecker = new TimedTaskAvailableChecker() {
        @Override
        protected int getExecInterval() {
            return 1000 * 60 * 10;
        }
    };

    @Override
    protected int getMaxExecCount() {
        return Constant.TOU_TIAO_BROWSE_GOODS_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        if (timedTaskChecker.isTimedTaskAvailable()) {
            return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                    .getTouTiaoBrowseGoodsExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseTouTiaoBrowseGoodsExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;
//
//        // swipe to bottom
//        UiDriver.swipeUp800pxFast();
//        Thread.sleep(2000);
//        UiDriver.swipeUp800pxFast();
//        Thread.sleep(2000);

        // click the more task
        if (UiDriver.swipeUpToFindAndClickObject(new UiSelector().text(Constant.STR_TOU_TIAO_MORE_TASK).instance(1))) {
            Logger.debug("open more task list success");

            UiSelector uiSelector = new UiSelector().text(Constant.STR_TOU_TIAO_BROWSE_GOODS_TO_GET_GOLD);
            if (UiDriver.swipeUpToFindAndClickObject(uiSelector)) {
                if (null != UiDriver.find(new UiSelector().text(Constant.STR_TOU_TIAO_BROWSE_GOODS_30S), Constant.WAIT_TIME_10_SEC)) {
                    Thread.sleep(1000 * 35);
                    UiDriver.pressBack();
                    Thread.sleep(1000 * 5);
                    result = true;
                } else {
                    Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), open browse goods window failed");
                }
            } else {
                Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), click to open browse goods window failed");
            }

            if (result) {
                timedTaskChecker.setLastSuccessTime(System.currentTimeMillis());
            }
        } else {
            Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), click more task button failed");
        }



        Logger.debug("TouTiaoBrowseGoodsTimedTask.autoBrowse(), result = " + result);
        return result;
    }

}
