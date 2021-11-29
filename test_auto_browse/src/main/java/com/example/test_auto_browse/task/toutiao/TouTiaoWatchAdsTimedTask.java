package com.example.test_auto_browse.task.toutiao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.task.TimedTaskAvailableChecker;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class TouTiaoWatchAdsTimedTask extends TouTiaoBaseTask {
    private TouTiaoWatchAdsTimedTask() {}
    private static TouTiaoWatchAdsTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new TouTiaoWatchAdsTimedTask();
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
        return Constant.TOU_TIAO_WATCH_ADS_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        if (timedTaskChecker.isTimedTaskAvailable()) {
            return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                    .getTouTiaoWatchAdsExecCount();
        } else {
            return 0;
        }
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseTouTiaoWatchAdsExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // swipe up to click button to open the watch ads window
        UiSelector uiSelector = new UiSelector().text(Constant.STR_TOU_TIAO_WATCH_ADS_TO_GET_GOLD);
        if (UiDriver.swipeUpToFindAndClickObject(uiSelector, 7)) {
            touTiaoWatchAds();
            result = true;
        } else {
            Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), click watch ads failed");
        }

        if (result) {
            timedTaskChecker.setLastSuccessTime(System.currentTimeMillis());
        }

        Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), result = " + result);
        return result;
    }

}
