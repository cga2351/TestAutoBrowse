package com.example.test_auto_browse.task.douyin;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DouYinWatchAdsTimedTask extends DouYinBaseTask {
    private DouYinWatchAdsTimedTask() {}
    private static DouYinWatchAdsTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DouYinWatchAdsTimedTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DOU_YIN_BROWSE_ADS_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getDouYinBrowseAdsExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDouYinBrowseAdsExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        Thread.sleep(3000);
        if (UiDriver.swipeUpToFindAndClickObject(new UiSelector().description(Constant.STR_DOU_YIN_WATCH_ADS_TO_GET_GOLD))) {
            // watch ads
            result = douYinWatchAds();
            Logger.debug("DouYinWatchAdsTimedTask.autoBrowse(), watch ads result = " + result);
        } else {
            Logger.debug("DouYinWatchAdsTimedTask.autoBrowse(), click watch ads failed");
        }

        Logger.debug("DouYinWatchAdsTimedTask.autoBrowse(), result=" + result);
        return result;
    }
}
