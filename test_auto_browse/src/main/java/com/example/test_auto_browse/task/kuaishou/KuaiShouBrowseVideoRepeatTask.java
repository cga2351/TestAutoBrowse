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
 * date : 2021/6/10
 * description :
 */

public class KuaiShouBrowseVideoRepeatTask extends KuaiShouBaseTask {
    private KuaiShouBrowseVideoRepeatTask() {}
    private static KuaiShouBrowseVideoRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new KuaiShouBrowseVideoRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.KUAI_SHOU_BROWSE_VIDEO_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getKuaiShouBrowseVideoExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseKuaiShouBrowseVideoExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            UiSelector uiSelector = new UiSelector().text(Constant.STR_KUAI_SHOU_WATCH_VIDEO_TO_GET_GOLD);
            if (UiDriver.swipeUpToFindObject(uiSelector)) {
                Thread.sleep(2000);
                if (UiDriver.findAndClick(uiSelector)) {
                    result = true;
                    Logger.debug("KuaiShouBrowseVideoRepeatTask.initTask(), success");
                } else {
                    Logger.debug("KuaiShouBrowseVideoRepeatTask.initTask(), click watch video failed");
                }
            } else {
                Logger.debug("KuaiShouBrowseVideoRepeatTask.initTask(), can't find watch video button");
            }
        } else {
            Logger.debug("KuaiShouBrowseVideoRepeatTask.initTask(), super.initTask() failed");
        }

        Logger.debug("KuaiShouBrowseVideoRepeatTask.initTask(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("KuaiShouBrowseVideoRepeatTask.autoBrowse(), entry");

        while (!getForceStop()) {
            // auto watch video for 12s, and swipe to next video
            Thread.sleep(8000);
            UiDriver.swipeUp800pxFast();
            while (null != UiDriver.find(new UiSelector().textContains(Constant.STR_KUAI_SHOU_CLICK_TO_OPEN), 2000)) {
                UiDriver.swipeUp800pxFast();
            }
        }

        Logger.debug("KuaiShouBrowseVideoRepeatTask.autoBrowse(), exit");
        return true;
    }

}