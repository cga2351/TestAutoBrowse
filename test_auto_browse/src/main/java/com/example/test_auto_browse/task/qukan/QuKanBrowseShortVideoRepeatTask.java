package com.example.test_auto_browse.task.qukan;

import com.android.uiautomator.core.UiSelector;
import com.example.test_auto_browse.Constant;
import com.example.test_auto_browse.UiDriver;
import com.example.test_auto_browse.task.IBrowseTask;
import com.example.test_auto_browse.utils.LocalStorageUtil;
import com.example.test_auto_browse.utils.Logger;

public class QuKanBrowseShortVideoRepeatTask extends QuKanBaseTask {
    private QuKanBrowseShortVideoRepeatTask() {}
    private static QuKanBrowseShortVideoRepeatTask instance;
    public static IBrowseTask getInstance() {
        if (null == instance) {
            synchronized (IBrowseTask.class) {
                if (null == instance) {
                    instance = new QuKanBrowseShortVideoRepeatTask();
                }
            }
        }
        return instance;
    }

    @Override
    protected int getMaxExecCount() {
        return Constant.QU_KAN_BROWSE_SHORT_VIDEO_MAX_EXEC_COUNT;
    }

    @Override
    protected int getLeftExecCount() {
        return getMaxExecCount() -
                LocalStorageUtil.getCachedTaskExecCount().getQuKanBrowseShortVideoExecCount();
    }

    @Override
    public void increaseExecCount() {
        LocalStorageUtil.updateCachedTaskExecCount(LocalStorageUtil.getCachedTaskExecCount()
                .increaseQuKanBrowseShortVideoExecCount());
    }

    @Override
    public boolean initTask() throws InterruptedException {
        boolean result = false;
        if (super.initTask()) {
            // switch to short video tab
            if (UiDriver.findAndClick(new UiSelector().text(Constant.STR_QU_KAN_SHORT_VIDEO_PAGE))) {
                if (null != UiDriver.find(new UiSelector().text(Constant.STR_QU_KAN_SHORT_VIDEO_SHARE))) {
                    result = true;
                } else {
                    Logger.debug("QuKanBrowseShortVideoRepeatTask.init(), switch to short video tab failed");
                }
            } else {
                Logger.debug("QuKanBrowseShortVideoRepeatTask.init(), click short video tab failed");
            }
        } else {
            Logger.debug("QuKanBrowseShortVideoRepeatTask.init(), super.initTask() failed");
        }

        Logger.debug("QuKanBrowseShortVideoRepeatTask.init(), result=" + result);
        return result;
    }

    @Override
    public boolean autoBrowse() throws InterruptedException {
        Logger.debug("QuKanBrowseShortVideoRepeatTask.autoBrowse(), entry");

        while (!getForceStop()) {
            // auto watch video for 15s, and swipe to next video
            Thread.sleep(8000);
            UiDriver.swipeUp1000pxFast();

            if (!haveClickedBonus) {
                Logger.debug("QuKanBrowseShortVideoRepeatTask.autoBrowse(), click bonus button");
                clickBonusIcon();
                haveClickedBonus = true;
            }
        }

        Logger.debug("QuKanBrowseShortVideoRepeatTask.autoBrowse(), exit");
        return true;
    }
}
