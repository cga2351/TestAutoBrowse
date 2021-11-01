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
        return 1000 * 60 * 10 + 1000 * 30;
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

        UiSelector uiSelector = new UiSelector().textContains(Constant.STR_KUAI_SHOU_1100_GOLD_WATCH_LIVE_TASK);
        if (UiDriver.swipeUpToFindObject(uiSelector)) {
            if (UiDriver.findAndClick(uiSelector)) {

                // new version kuaishou has this step
                if (UiDriver.findAndClick(new UiSelector().resourceId(Constant.STR_KUAI_SHOU_LIVE_LIST_TITLE))) {
                    Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), select a live window");
                    if (UiDriver.findAndClick(new UiSelector().resourceId(Constant.STR_KUAI_SHOU_LIVE_LIST_ITEM))) {
                        Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), click live window item success");
                    } else {
                        Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), click live window item failed");
                    }
                }

                // check if enter the live window success
                if (null != UiDriver.find(new UiSelector().resourceId(Constant.ID_KUAI_SHOU_AWARD_COUNT_DOWN), Constant.WAIT_TIME_10_SEC) ||
                        null != UiDriver.find(new UiSelector().resourceId(Constant.ID_KUAI_SHOU_AWARD_COUNT_DOWN_NEW_VERSION), Constant.WAIT_TIME_10_SEC)) {
                    Thread.sleep(1000 * 90 + 1000 * 10);
                    // check live count down end
                    if (checkLiveCountDownEnd(1000 * 60)) {
                        Thread.sleep(2000);
                        UiDriver.pressBack();

                        if (null != UiDriver.find(new UiSelector().text(Constant.STR_KUAI_SHOU_FOLLOW_AND_EXIT))) {
                            UiDriver.findAndClick(new UiSelector().text(Constant.STR_KUAI_SHOU_EXIT));
                            Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), close the follow popup dialog");
                        }

                        // new version need to exit the live list window and re-enter the live window
                        if (null != UiDriver.find(new UiSelector().resourceId(Constant.STR_KUAI_SHOU_LIVE_LIST_TITLE))) {
                            Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), new version, need to exit the live list page");
                            UiDriver.pressBack();
                        }

                        result = true;
                    } else {
                        Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), wait live count down end failed");
                    }
                } else {
                    Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), enter live window failed");
                }
            } else {
                Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), click 1100 live task failed");
            }
        } else {
            Logger.debug("KuaiShouWatchLiveRepeatTask.autoBrowse(), find 1100 live task button failed");
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
