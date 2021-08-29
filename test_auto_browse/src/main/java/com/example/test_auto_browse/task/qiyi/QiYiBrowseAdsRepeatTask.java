package com.example.test_auto_browse.task.qiyi;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class QiYiBrowseAdsRepeatTask extends QiYiBaseTask {
    private QiYiBrowseAdsRepeatTask() {}
    private static QiYiBrowseAdsRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new QiYiBrowseAdsRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.QI_YI_BROWSE_ADS_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getQiYiBrowseAdsExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseQiYiBrowseAdsExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        long startTime = System.currentTimeMillis();
        boolean needRetry = true;
        while (needRetry && (System.currentTimeMillis() - startTime) < 1000 * 60) {
            if (UiDriver.findAndClick(new UiSelector().descriptionContains(Constant.STR_QI_YI_WATCH_ADS)) ||
                    UiDriver.findAndClick(new UiSelector().text(Constant.STR_QI_YI_GET_MORE_100_GOLD))) {
                if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_QI_YI_WAIT_ADS_END))) {
                    // wait ads end for max 60s
                    result = watchAds(1000 * 60);
                    Logger.debug("QiYiBrowseAdsRepeatTask.autoBrowse(), watch ads result = " + result);

                    // enter ads page success, need not to retry
                    needRetry = false;
                } else {
                    if (null != UiDriver.find(new UiSelector().resourceId("com.qiyi.video.lite:id/action_bar_root"))) {
                        Logger.debug("QiYiBrowseAdsRepeatTask.autoBrowse(), open ads page, but can't find ads count down object, press back directly");
                        UiDriver.pressBack();

                        // enter ads page success, but ads didn't play, need to retry
                        needRetry = true;
                    } else {
                        Logger.debug("QiYiBrowseAdsRepeatTask.autoBrowse(), open watch ads page failed");
                    }
                }
            } else {
                if (null != UiDriver.find(new UiSelector().text(Constant.STR_QI_YI_OK_I_KNOW))) {
                    Logger.debug("QiYiBrowseAdsRepeatTask.autoBrowse(), total 10 ads have been watched, exit task");
                } else {
                    Logger.debug("QiYiBrowseAdsRepeatTask.autoBrowse(), click watch ads failed");
                }

                // enter ads page failed, need not to retry
                needRetry = false;
            }
        }

        Logger.debug("QiYiBrowseAdsRepeatTask.autoBrowse(), result=" + result);
        return result;
    }

    private boolean watchAds(long maxWatchDuration) throws InterruptedException {
        boolean result = false;

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < maxWatchDuration) {
            if (null == UiDriver.find(new UiSelector().textContains(Constant.STR_QI_YI_WAIT_ADS_END))) {
                Logger.debug("QiYiBrowseAdsRepeatTask.watchAds(), watch ads end");
                result = true;
                break;
            }
            Thread.sleep(5000);
        }

        // return to gold page
        UiDriver.pressBack();

        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QI_YI_CLOSE_ADS))) {
            result = false;
            Logger.debug("QiYiBrowseAdsRepeatTask.watchAds(), watch ads not success, close forcibly");
        }

        Logger.debug("QiYiBrowseAdsRepeatTask.watchAds(), result=" + result);
        return result;
    }
}
