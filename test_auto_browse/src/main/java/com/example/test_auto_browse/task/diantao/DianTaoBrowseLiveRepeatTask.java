package com.example.test_auto_browse.task.diantao;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class DianTaoBrowseLiveRepeatTask extends DianTaoBaseTask {
    private DianTaoBrowseLiveRepeatTask() {}
    private static DianTaoBrowseLiveRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new DianTaoBrowseLiveRepeatTask();
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
        return Constant.DIAN_TAO_BROWSE_LIVE_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() - LocalStorageUtil.getCachedTaskExecCount()
                .getDianTaoBrowseLiveExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseDianTaoBrowseLiveExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            UiSelector uiSelector = new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_GET_GOLD);
            if (UiDriver.swipeUpToFindObject(uiSelector)) {
                // click watch live to get gold
                result = true;
            } else {
                Logger.debug("DianTaoBrowseVideoTask.initTask(), swipe up to find watch live failed");
            }
        } else {
            Logger.debug("DianTaoBrowseLiveTask.initTask(), super.initTask() failed");
        }

        Logger.debug("DianTaoBrowseLiveTask.initTask(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("DianTaoBrowseLiveTask.autoBrowse(), entry");

        while (!getForceStop()) {
            // click "看直播，赚元宝" may failed, try forever
            while (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_GET_GOLD))) {
                Thread.sleep(1000);
            }

            watchVideoOrLive(1000 * 60 * 3, false, true);

//            // auto watch live for 12s, and get gold
//            Thread.sleep(15000);
//            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_GOLD))) {
//                Logger.debug("DianTaoBrowseLiveTask.autoBrowse(), click get gold");
//            }
//            UiDriver.pressBack();
//            Thread.sleep(3000);
        }

        Logger.debug("DianTaoBrowseLiveTask.autoBrowse(), exit");
        return true;
    }
}
