package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoBrowseVideoRepeatTask extends DianTaoBaseTask {
    private DianTaoBrowseVideoRepeatTask() {}
    private static DianTaoBrowseVideoRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoBrowseVideoRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    public int waitTaskEndMaxTime() {
        return 1000 * 60 * 3 + 1000 * 30;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.DIAN_TAO_BROWSE_VIDEO_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                .getDianTaoBrowseVideoExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoBrowseVideoExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_VIDEO_GET_GOLD);
            if (UiDriver.swipeUpToFindObject(uiSelector)) {
                // click watch video to get gold
                result = true;
            } else {
                Logger.debug("DianTaoBrowseVideoTask.initTask(), swipe up to find watch video failed");
            }
        } else {
            Logger.debug("DianTaoBrowseVideoTask.initTask(), super.initTask() failed");
        }

        Logger.debug("DianTaoBrowseVideoTask.initTask(), result=" + result);

        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("DianTaoBrowseVideoTask.autoBrowse(), entry");

        while (!getForceStop()) {
            // click "看视频，赚元宝" may failed, try forever
            while (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_VIDEO_GET_GOLD))) {
                Thread.sleep(1000);
            }

            watchVideoOrLive(1000 * 60 * 3, true, false);

//            if (UiDriver.findAndClick(new UiSelector().resourceId("com.taobao.live:id/gold_new_progress_get"), 3000)) {
//                Logger.debug("DianTaoBrowseVideoTask.autoBrowse(), click get new gold");
//            }
//
//            UiDriver.swipeUp600pxFast();
        }

        Logger.debug("DianTaoBrowseVideoTask.autoBrowse(), exit");
        return true;
    }

}
