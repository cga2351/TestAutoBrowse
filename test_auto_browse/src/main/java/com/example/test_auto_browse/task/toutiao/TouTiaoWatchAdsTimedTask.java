package com.example.test_auto_browse.task.toutiao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
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

    @Override
    protected int getMaxExecCount() {
        return Constant.TOU_TIAO_WATCH_ADS_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getTouTiaoWatchAdsExecCount();
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
        int swipeCount = 0;
        boolean foundWatchAds = false;
        while (swipeCount < 5 && !foundWatchAds) {
            if (null != UiDriver.findInVisibleRect(uiSelector)) {
                Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), found watch ads button");
                foundWatchAds = true;
            } else {
                // try to open the task list
                if(UiDriver.findAndClick(new UiSelector().text(Constant.STR_TOU_TIAO_MORE_TASK), 2000)) {
                    Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), open more task list");
                }

                // swipe up to find again
                UiDriver.swipeUp800pxSlowly();
                swipeCount++;
                Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), swipe up to find again, swipeCount = " + swipeCount);
            }
        }

        if (foundWatchAds) {
            if (UiDriver.findAndClick(uiSelector)) {
                touTiaoWatchAds();
                result = true;
            } else {
                Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), open watch ads to get gold window failed");
            }
        } else {
            Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), can't swipe up to find watch ads");
        }

        Logger.debug("TouTiaoWatchAdsTask.autoBrowse(), result = " + result);
        return result;
    }

}
