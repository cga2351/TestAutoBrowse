package com.example.test_auto_browse.task.kuaishou;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class KuaiShouWatchLiveRepeatTask extends KuaiShouBaseTask {
    private KuaiShouWatchLiveRepeatTask() {}
    private static KuaiShouWatchLiveRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new KuaiShouWatchLiveRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 90 * getMaxExecCount() + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.KUAI_SHOU_WATCH_LIVE_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getKuaiShouWatchLiveExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseKuaiShouWatchLiveExecCount());
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        boolean result = false;

        UiSelector uiSelector = new UiSelector().text(Constant.STR_KUAI_SHOU_WATCH_LIVE_GET_GOLD);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {
                Thread.sleep(1000 * 90 + 1000 * 10);
            }

            // check live count down end
            if (checkLiveCountDownEnd(1000 * 60)) {
                Thread.sleep(2000);
                UiDriver.pressBack();

                if (null != UiDriver.find(new UiSelector().text(Constant.STR_KUAI_SHOU_FOLLOW_AND_EXIT))) {
                    UiDriver.findAndClick(new UiSelector().text(Constant.STR_KUAI_SHOU_EXIT));
                    Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), close the follow popup dialog");
                }

                result = true;
            } else {
                Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), wait live count down end failed");
            }
        }

        Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), result=" + result);
        return result;
    }

    private boolean checkLiveCountDownEnd(int maxWaitTime) throws InterruptedException {
        boolean result = false;
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < maxWaitTime) {
            if (null == UiDriver.find(new UiSelector().resourceId(Constant.ID_KUAI_SHOU_AWARD_COUNT_DOWN))) {
                result = true;
                break;
            }
            Thread.sleep(3000);
        }
        return result;
    }

}
