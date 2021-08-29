package com.example.test_auto_browse.task.kuaishou;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

/**
 * author : yuliang
 * mail : yuliang@navercorp.com
 * date : 2021/6/15
 * description :
 */

public class KuaiShouTreasureBoxTimedTask extends KuaiShouBaseTask {
    private KuaiShouTreasureBoxTimedTask() {}
    private static KuaiShouTreasureBoxTimedTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new KuaiShouTreasureBoxTimedTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.KUAI_SHOU_TREASURE_BOX_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getKuaiShouTreasureBoxExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseKuaiShouTreasureBoxExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        // open treasure box
        if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_KUAI_SHOU_OPEN_TREASURE_BOX))) {
            // watch ads
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_KUAI_SHOU_WATCH_VIDEO_TO_GET_MORE))) {
                kuaiShouWatchAds(1000 * 30);
            } else {
                Logger.debug("KuaiShouTreasureBoxTimedTask.autoBrowse(), no ads");
            }
            result = true;
        } else {
            Logger.debug("KuaiShouTreasureBoxTimedTask.autoBrowse(), open treasure box failed");
        }

        Logger.debug("KuaiShouTreasureBoxTimedTask.autoBrowse(), result=" + result);
        return result;
    }

    private void kuaiShouWatchAds(int adsDuration) throws InterruptedException {
        if (null != UiDriver.find(new UiSelector().textContains(Constant.STR_KUAI_SHOU_WAIT_ADS_END))) {
            Thread.sleep(adsDuration);

            // check if ads count down end
            if (kuaiShouWaitAdsCountDownEnd(1000 * 30)) {
                UiDriver.pressBack();
                Thread.sleep(2000);
                Logger.debug("KuaiShouTreasureBoxTimedTask.kuaiShouWatchAds(), watch ads success");
            } else {
                Logger.debug("KuaiShouTreasureBoxTimedTask.kuaiShouWatchAds(), wait ads end failed");
            }
        } else {
            Logger.debug("KuaiShouTreasureBoxTimedTask.kuaiShouWatchAds(), open watch ads page failed");
        }
    }

    private boolean kuaiShouWaitAdsCountDownEnd(int maxWaitTime) throws InterruptedException {
        // find ads end text, wait most maxWaitTime
        boolean result = false;
        long startWaitTime = System.currentTimeMillis();
        UiSelector uiSelector = new UiSelector().textContains(Constant.STR_KUAI_SHOU_WAIT_ADS_END);
        while ((System.currentTimeMillis() - startWaitTime) < maxWaitTime) {
            if (null == UiDriver.find(uiSelector)) {
                result = true;
                break;
            }
            Thread.sleep(1000 * 2);
        }

        return result;
    }
}
