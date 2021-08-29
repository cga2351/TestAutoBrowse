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
            // swipe up to click watch video to get gold
            UiDriver.swipeUp800pxSlowly();
            Thread.sleep(1000);
            UiDriver.swipeUp800pxSlowly();

            // click watch video to get gold
            if (null != UiDriver.find(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_GET_GOLD))) {
                result = true;
            } else {
                Logger.debug("DianTaoBrowseLiveTask.initTask(), no watch live to get gold");
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
            // check if has get gold on the right top
            getGoldOnRightTop();

            // click "看直播，赚元宝" may failed, try forever
            while (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_WATCH_LIVE_GET_GOLD))) {
                Thread.sleep(1000);
            }

            // auto watch live for 12s, and get gold
            Thread.sleep(15000);
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_DIAN_TAO_GET_GOLD))) {
                Logger.debug("DianTaoBrowseLiveTask.autoBrowse(), click get gold");
            }
            UiDriver.pressBack();
            Thread.sleep(3000);
        }

        Logger.debug("DianTaoBrowseLiveTask.autoBrowse(), exit");
        return true;
    }
}
