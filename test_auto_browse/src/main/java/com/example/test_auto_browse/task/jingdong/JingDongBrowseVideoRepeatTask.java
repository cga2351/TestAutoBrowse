package com.example.test_auto_browse.task.jingdong;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class JingDongBrowseVideoRepeatTask extends JingDongBaseTask {
    private JingDongBrowseVideoRepeatTask() {}
    private static JingDongBrowseVideoRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new JingDongBrowseVideoRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.JING_DONG_BROWSE_VIDEO_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getJingDongBrowseVideoExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseJingDongBrowseVideoExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            UiSelector uiSelector = new UiSelector().text(Constant.STR_JING_DONG_BROWSE_VIDEO_GET_GOLD);
            if (UiDriver.swipeUpToFindObject(uiSelector)) {
                if (UiDriver.findAndClick(uiSelector)) {
                    if (null != UiDriver.find(new UiSelector().resourceId(Constant.ID_JING_DONG_GOLD_PROGRESS))) {
                        result = true;
                    } else {
                        Logger.debug("JingDongBrowseVideoRepeatTask.initTask(), open browse video page failed");
                    }
                } else {
                    Logger.debug("JingDongBrowseVideoRepeatTask.initTask(), click browse video failed");
                }
            } else {
                Logger.debug("JingDongBrowseVideoRepeatTask.initTask(), find browse video btn failed");
            }
        } else {
            Logger.debug("JingDongBrowseVideoRepeatTask.initTask(), super.initTask() failed");
        }

        Logger.debug("JingDongBrowseVideoRepeatTask.initTask(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("JingDongBrowseVideoRepeatTask.autoBrowse(), entry");

        while (!getForceStop()) {
            // watch video for a while and swipe to next
            Thread.sleep(8000);
            UiDriver.swipeUp600pxFast();
        }

        Logger.debug("JingDongBrowseVideoRepeatTask.autoBrowse(), exit");
        return true;
    }

}
